package TorrentServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import BitFieldsBuilder.BitFieldsBuilder;
import BitTorrentProtocol.MessageController;
import BitTorrentProtocol.MessageType;
import BitTorrentProtocol.TorrentHandshake;
import BitTorrentProtocol.TorrentPiece;
import BitTorrentProtocol.TorrentRequest;
import FileManager.FileManager;
import PeerModel.Peer;
import PeerModel.PeerStatus;
import Torrent.Torrent;

public class TorrentServer implements Runnable {
    private int port = 0;
    private int serverTimeout = 0;
    private int clientTimeout = 0;

    public TorrentServer(int port, int serverSocketTimeout, int clientSocketTimeout) {
        this.port = port;
        this.serverTimeout = serverSocketTimeout;
        this.clientTimeout = clientSocketTimeout;
    }

    public void setWorkingDirectoryName(String dirName) {
        fileManager.setWorkingDirectoryName(dirName);
    }

    public String getWorkingDirectory() {
        return fileManager.getWorkingDirectory();
    }

    private final static Logger logger = LogManager.getLogger(TorrentServer.class);
    private FileManager fileManager = new FileManager();


    public void run() {
        try(ServerSocketChannel serverChannel = ServerSocketChannel.open(); Selector selector = Selector.open()) {
            System.out.println("Torrent Server listening on port: " + port);

            serverChannel.socket().setSoTimeout(serverTimeout);
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        try {
                            handleAccept(key, selector);
                        } catch(IOException e) {
                            System.err.println(e.getMessage());
                            key.cancel();
                        }
                    } else if (key.isReadable()) {
                        try {
                            handleRead(key);
                        } catch(IOException e) {
                            System.err.println(e.getMessage());
                            key.cancel();
                        }
                    } else {
                        key.cancel();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAccept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.socket().setSoTimeout(clientTimeout);

        Peer peer = new Peer(clientChannel.socket().getInetAddress().getHostAddress(), clientChannel.socket().getPort());

        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ, peer);

        peer.setStatus(PeerStatus.Connected);

        System.out.println("Accepted connection from: " + clientChannel.getRemoteAddress());
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        Peer handlingPeer = (Peer) key.attachment();
        ByteBuffer buffer = null;
        switch (handlingPeer.getStatus()) {
            case Connected:
                buffer = readHandshake(channel);
                break;
            case RecivedBitField:
                buffer = readMessage(channel);
                break;
            default:
                break;
        } 

        if (buffer == null) {
            return;
        }

        processReceivedData(channel, buffer.array(), (Peer) key.attachment());
    }

    private ByteBuffer readMessage(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        if(channel.read(buffer) < 0) {
            // Connection closed by the client
            channel.close();
            System.out.println("Connection closed by: " + channel.getRemoteAddress());
            return null;
        }

        buffer.flip(); 

        int expectedSize = buffer.getInt();
        buffer = ByteBuffer.allocate(expectedSize);
        
        while (buffer.hasRemaining()) {
            if (channel.read(buffer)  < 0) {
                // Connection closed by the client
                channel.close();
                System.out.println("Connection closed by: " + channel.getRemoteAddress());
                return null;
            }
        }                

        buffer.flip();
        return buffer;
    }


    private ByteBuffer readHandshake(SocketChannel channel) throws IOException {
        var buffer = ByteBuffer.allocate(68);
        while (buffer.hasRemaining()) {
            channel.read(buffer);
        }
        buffer.flip();

        return buffer;
    }

    private Torrent torrent = null;

    private void processReceivedData(SocketChannel channel, byte[] data, Peer peer) throws IOException {
        switch (peer.getStatus()) {
            case Connected:
                if (MessageController.getMessageType(data) != MessageType.HANDSHAKE) {
                    logger.error("First msg is not handshake, sorry ...");
                    channel.close();
                    return;
                }

                byte[] infoHash = TorrentHandshake.parseInfoHash(data);
                torrent = getTorrentByHash(infoHash);
                if (torrent == null) {
                    logger.info("Cannot find requested .torrent file, sorry\n");
                    logger.info("Connection is closed");
                    return;
                }

                logger.info("Found " + torrent.getName() + " with hash " + new String(torrent.getInfoHash()));              

                sendHandshake(infoHash, peerId.getBytes(), channel);
                sendBitfield(channel);
                peer.setStatus(PeerStatus.RecivedBitField);
                break;
            case RecivedBitField: 
                if (MessageController.getMessageType(data) != MessageType.REQUEST) {
                    logger.error("Recive " + MessageController.getMessageType(data) + ", but it does not request, sorry ...");
                    channel.close();
                    return;
                }

                handleRequest(new TorrentRequest(data), channel);
            default:
                break;
        }
    }

    protected Torrent getTorrentByHash(byte[] infoHash) {
        return fileManager.getTorrentByHash(infoHash);
    }

    private void handleRequest(TorrentRequest request, SocketChannel channel) throws IOException { 
        int offsetInSource = request.getPieceIndex() * torrent.getPieceLength() + request.getOffset();
        byte[] data = fileManager.readFromFile(torrent.getName(), offsetInSource, request.getBlockLength());
        var piece = new TorrentPiece(request.getPieceIndex(), request.getOffset(), data);
        channel.write(ByteBuffer.wrap(piece.getMessage()));
    }

    private void sendBitfield(SocketChannel channel) throws IOException {
        var bitfieldMessage = MessageController.generateBitfieldMessage(BitFieldsBuilder.getBitField(torrent, fileManager));
        channel.write(ByteBuffer.wrap(bitfieldMessage));
    }

    private final String peerId = "01234566789";
    private void sendHandshake(byte[] infoHash, byte[] peerId, SocketChannel channel) throws IOException {
        byte[] handshakeMessage = TorrentHandshake.buildHandShakeData(infoHash, peerId);
        channel.write(ByteBuffer.wrap(handshakeMessage));
        logger.info("Handshake to client: " + new String(HexFormat.of().formatHex(infoHash)));
    }
}
