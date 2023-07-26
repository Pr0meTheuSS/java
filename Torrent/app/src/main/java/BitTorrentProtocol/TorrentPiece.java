/*
 * Project: YourProjectName
 * Created Date: Thursday, June 1st 2023, 10:18:21 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package BitTorrentProtocol;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class TorrentPiece {
    public TorrentPiece(byte[] msg) {
        ByteBuffer bb = ByteBuffer.wrap(msg);
        int blockLength = msg.length - 9;
        pieceIndex = bb.getInt(1);
        begin = bb.getInt(5);
        block = new byte[blockLength];
        block = Arrays.copyOfRange(msg, 1 + 4  + 4, msg.length);
        message = msg;
    }

    public TorrentPiece(int pieceId, int begin, byte[] block) {
        pieceIndex = pieceId;
        this.begin = begin;
        this.block = block;
        
        ByteBuffer bb = ByteBuffer.allocate(4 + 9 + block.length);
        // Set msg length.
        bb.putInt(9 + block.length);
        // Set msg id.
        bb.put((byte)7);
        bb.putInt(pieceId);
        bb.putInt(begin);
        // Set msg payload.
        bb.put(block);

        message =  bb.array();
      }

    public int getPieceIndex() {
        return pieceIndex;
    }

    public int getBegin() {
        return begin;
    }

    public byte[] getBlock() {
        return block;
    }

    public byte[] getMessage() {
        return message;
    }

    protected int messageId = 7;

    protected int pieceIndex = 0;
    protected int begin = 0;
    protected byte[] block = null;
    protected byte[] message = null;
}
