/*
 * Project: Torrent
 * Created Date: Tuesday, May 30th 2023, 11:45:46 pm
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
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TorrentHandshake {
    public static byte[] parseInfoHash(byte[] data) {
        ByteBuffer bb =  ByteBuffer.wrap(data);
        if (!checkProtocol(data)) {
            logger.error("Wrong protocol name");
            return null;
        }

        int protocolLength = (int) bb.get(0);
        byte[] infoHash = new byte[20];
        bb = bb.position(protocolLength + 8 + 1);
        bb.get(infoHash, 0, 20);
        return infoHash;
    }
    
    public static boolean checkProtocol(byte[] data) {
        ByteBuffer bb =  ByteBuffer.wrap(data);
        int protocolLength = (int) bb.get(0);
        if (protocolLength != protocolName.length()) {
            return false;
        }

        String recivedProtocolName = new String(data, 1, protocolLength, StandardCharsets.UTF_8);
        return recivedProtocolName.equals(protocolName);
    }

    public static byte[] buildHandShakeData(byte[] infoHash, byte[] peerId) {
        ByteBuffer bb =  ByteBuffer.allocate(1 + 8 +  20 * 2 + protocolName.length());    
        bb.put((byte) protocolName.length());
        bb.put(protocolName.getBytes());
        bb.put(zeros);
        bb.put(infoHash);
        bb.put(peerId);
        return bb.array();
    }

    private final static byte[] zeros = {0, 0, 0, 0, 0, 0, 0, 0};
    private final static Logger logger = LogManager.getLogger(TorrentHandshake.class);
    final private static String protocolName = "BitTorrent protocol";

}