package BitTorrentProtocol;
/*
 * Project: Torrent
 * Created Date: Tuesday, May 16th 2023, 9:30:18 pm
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
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class MessageController {
    static public byte[] generateBitfieldMessage(BitSet bitfield) {
        ByteBuffer bb = ByteBuffer.allocate(4 + 1 + bitfield.length());
        // Set msg length.
        bb.putInt(1 + bitfield.length());
        // Set msg id.
        bb.put((byte)5);
        // Set msg payload.
        bb.put(bitfield.toByteArray());

        return bb.array();
    }

    public static MessageType getMessageType(byte[] msg) {
        ByteBuffer bb = ByteBuffer.wrap(msg);
        byte id = bb.get(0);
        return isHandshake(msg) ? MessageType.HANDSHAKE : getMessageTypeById(id);
    }

    protected static boolean isHandshake(byte[] msg) {
        final String protocolName = "BitTorrent protocol";

        if (msg.length != 1 + protocolName.length() + 8 + 20 * 2) {
            return false;
        }

        ByteBuffer bb =  ByteBuffer.wrap(msg);
        int protocolLength = (int) bb.get(0);
        if (protocolLength != protocolName.length()) {
            return false;
        }

        String recivedProtocolName = new String(msg, 1, protocolLength, StandardCharsets.UTF_8);
        if (!recivedProtocolName.equals(protocolName)) {
            return false;
        }

        return true;
    }

    protected static MessageType getMessageTypeById(int id) {
        switch (id) {
            case 6: return MessageType.REQUEST;
            case 7: return MessageType.PIECE;                
            case 5: return MessageType.BITFIELD;
            default:
                return MessageType.UNKNOWN;
        }
    }
}
