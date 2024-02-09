/*
 * Project: Torrent
 * Created Date: Wednesday, May 10th 2023, 2:33:55 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package TorrentClient;
 
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import BitFieldsBuilder.BitFieldsBuilder;
import BitTorrentProtocol.*;
import FileManager.FileManager;
import Hasher.Hasher;
import Parser.Parser;
import Torrent.Torrent;
import PeerModel.Peer;
import PeerModel.PeerStatus;
import ConsoleView.*;

public class TorrentClient implements Runnable {
    private String torrentFile;
    private List<Peer> peers = null;

    public TorrentClient(String torrentFile) {
        this.torrentFile = torrentFile;
        this.peers = new ArrayList<>();
    }

    public void run() {
        loadTorrentFile();
        while (!isFileLoaded()) {
            loadFileFromPeers();
        }

        System.out.println("File loaded");
    }

    public void addPeer(String ip, int port) {
        peers.add(new Peer(ip, port));
    }

    private void loadTorrentFile() {
        Parser p = new Parser();
        torrent = p.parseTorrent(torrentFile);
        bitField = BitFieldsBuilder.getBitField(torrent, fileManager);
    }

    private boolean isFileLoaded() {
        bitField = BitFieldsBuilder.getBitField(torrent, fileManager);
        if (bitField.length() == 0) {
            return false;
        }

        return bitField.cardinality() == torrent.getPieces().size();
    }

    private void loadFileFromPeers() {
        try (Selector selector = Selector.open()) {
            while (!isFileLoaded()) {
                for (Peer peer : peers) {
                    if (peer.getStatus() == PeerStatus.NotConnected) {
                        SocketChannel channel = SocketChannel.open();
                        channel.configureBlocking(false);
                        channel.connect(new InetSocketAddress(peer.getIpAddress(), peer.getPort()));
                        channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE, peer);
                    }
                }

                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    Peer peer = (Peer) key.attachment();

                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.configureBlocking(false);

                        boolean isFinishConnect = false;
                        try {
                            isFinishConnect = channel.finishConnect();
                        } catch (IOException e) {
                            peer.setStatus(PeerStatus.NotConnected);
                            key.cancel();
                            channel.close();
                            continue;
                        }

                        if (isFinishConnect) {
                            var current = (Peer) key.attachment();
                            current.setStatus(PeerStatus.Connected);
                        } else {
                            peer.setStatus(PeerStatus.NotConnected);
                            key.cancel();
                            channel.close();
                        }
                    }

                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        try {
                            handleReadablePeer(peer, channel);
                        } catch (IOException e) {
                            peer.setStatus(PeerStatus.NotConnected);
                            key.cancel();
                            channel.close();
                        }
                    }

                    if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        try {
                            handleWritablePeer(peer, channel);
                        } catch (IOException e) {
                            peer.setStatus(PeerStatus.NotConnected);
                            key.cancel();
                            channel.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            return;
        }
    }


    private void handleWritablePeer(Peer currentPeer, SocketChannel channel) throws IOException {
        switch(currentPeer.getStatus()) {
            case NotConnected:
                break;
            case Connected:
                sendHandshake(currentPeer, channel);
                break;
            case SentHandshake:
                break;
            case RecivedBitField:
                sendRequest(currentPeer, channel);
                break;
            default: 
                break;
        }
    }

    private void sendHandshake(Peer peer, SocketChannel channel) throws IOException {
        byte[] handshake = TorrentHandshake.buildHandShakeData(torrent.getInfoHash(), "12345678901234567890".getBytes());
        ByteBuffer buffer = ByteBuffer.wrap(handshake);
        channel.write(buffer);
        peer.setStatus(PeerStatus.SentHandshake);
    }

    private void sendRequest(Peer peer, SocketChannel channel) throws IOException {
        int requestPieceIndex = bitField.nextClearBit(0); if (requestPieceIndex >= torrent.getPieces().size()) {
            return;
        }

        for (int i = requestPieceIndex; i < peer.getBitField().length(); i = bitField.nextClearBit(i + 1)) {
            if (peer.getBitField().get(i)) {
                bitField.set(i);
                TorrentRequest request = new TorrentRequest(i, 0, torrent.getPieceLength());
                channel.write(ByteBuffer.wrap(request.getMessage()));
                return;
            }
        }
    }

    private void handleReadablePeer(Peer currentPeer, SocketChannel channel) throws IOException {
        switch (currentPeer.getStatus()) {
            case SentHandshake:
                ByteBuffer buffer = ByteBuffer.allocate(68);
                if(channel.read(buffer) < 0) {
                    throw new IOException("socket channel is not readable");
                }
                buffer.flip(); 
                reciveHandshake(currentPeer, buffer);
                break;
            case RecivedHandShake:
                buffer = ByteBuffer.allocate(4);
                if(channel.read(buffer) < 0) {
                    throw new IOException("socket channel is not readable");
                }
                buffer.flip(); 
                int expectedSize = buffer.getInt();
                buffer = ByteBuffer.allocate(expectedSize);
                
                while (buffer.hasRemaining()) {
                    channel.read(buffer);
                }                

                buffer.flip();
                recivePeerBitfield(currentPeer, buffer);
                break;
            case RecivedBitField:
                buffer = ByteBuffer.allocate(4);
                if(channel.read(buffer) < 0) {
                    throw new IOException("socket channel is not readable");
                }
                buffer.flip();

                expectedSize = buffer.getInt();
                buffer = ByteBuffer.allocate(expectedSize);
                
                while (buffer.hasRemaining()) {
                    channel.read(buffer);
                }

                buffer.flip();
                recivePiece(currentPeer, buffer);
                break;
            default: 
                break;
        }
    }

    protected void reciveHandshake(Peer peer, ByteBuffer data) throws IOException {
        if (MessageController.getMessageType(data.array()) != MessageType.HANDSHAKE) {
            logger.error("First message is not handshake, sorry ...");
        }

        peer.setStatus(PeerStatus.RecivedHandShake);
        logger.info("Handshake from server recived");
    }

    protected void recivePeerBitfield(Peer peer, ByteBuffer data) throws IOException {
        if (MessageController.getMessageType(data.array()) != MessageType.BITFIELD) {
            logger.error("Bitfield message has invalid format, sorry ...");
            return;
        }

        byte[] bitfield = Arrays.copyOfRange(data.array(), 1, data.array().length); 
        peer.setBitField(BitSet.valueOf(bitfield));

        peer.setStatus(PeerStatus.RecivedBitField);
        logger.info("Bitfield from server recived");
    }


    protected void recivePiece(Peer peer, ByteBuffer data) {
        try  {
            TorrentPiece piece = new TorrentPiece(data.array());
            int uselessBytes = (piece.getPieceIndex() == torrent.getPieces().size() - 1) ? torrent.getPieceLength() * (piece.getPieceIndex() + 1) - (int)torrent.getFileLength() : 0;
            byte[] block = Arrays.copyOfRange(piece.getBlock(), 0, torrent.getPieceLength() - uselessBytes);
            long offset = (long)piece.getPieceIndex() * torrent.getPieceLength() + (long)piece.getBegin();

            if (Arrays.equals(Hasher.getSHA1(block), torrent.getPieces().get(piece.getPieceIndex()))) {
                fileManager.writeToFile(torrent.getName(), offset, block);
                bitField.set(piece.getPieceIndex());
               view.setLoadingStatus(bitField.cardinality(), torrent.getPieces().size());
            } else {
                bitField.clear(piece.getPieceIndex());
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void setWorkingDirectoryName(String dirName) {
        fileManager.setWorkingDirectoryName(dirName);
    }

    public String getWorkingDirectory() {
        return fileManager.getWorkingDirectory();
    }

    ConsoleView view = new ConsoleView();
    BitSet bitField = null;
    Torrent torrent = null;
    private final static Logger logger = LogManager.getLogger(TorrentClient.class);
    private FileManager fileManager = new FileManager();
}
