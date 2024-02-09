package BitTorrentProtocol;
/*
 * Project: Torrent
 * Created Date: Tuesday, May 16th 2023, 9:31:04 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */

public enum MessageType {
    HANDSHAKE,
    PIECE,
    REQUEST,
    BITFIELD,
    UNKNOWN
}
