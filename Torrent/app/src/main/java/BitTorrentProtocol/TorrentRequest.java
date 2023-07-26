package BitTorrentProtocol;
/*
 * Project: Torrent
 * Created Date: Tuesday, May 16th 2023, 9:34:57 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */

import java.nio.ByteBuffer;

public class TorrentRequest {
 
    public TorrentRequest(byte[] msg) {
        ByteBuffer bb = ByteBuffer.wrap(msg);
        pieceIndex = bb.getInt(1);
        offset = bb.getInt(5);
        blockLength = bb.getInt(9);
        message = msg;
    }

    public TorrentRequest(int pieceId, int offsetInPiece, int partLenth) {
        pieceIndex = pieceId;
        offset = offsetInPiece;
        blockLength = partLenth;
        // Длина сообщения + 4 байта под хранение этой длины.
        message = new byte[messageLength + 4];

        ByteBuffer bb = ByteBuffer.wrap(message);
        bb.putInt(messageLength);
        bb.put((byte) messageId);
        bb.putInt(pieceIndex);
        bb.putInt(offset);
        bb.putInt(blockLength);

        message = bb.array();
    }


    public int getPieceIndex() {
        return pieceIndex;
    }

    public int getOffset() {
        return offset;
    }

    public int getBlockLength() {
        return blockLength;
    }
    
    public byte[] getMessage() {
        return message;
    }

    protected int messageId = 6;
    protected int messageLength = 13;

    protected int pieceIndex = 0;
    protected int offset = 0;
    protected int blockLength = 0;
    protected byte[] message = null;
}
