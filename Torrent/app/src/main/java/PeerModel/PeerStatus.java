/*
 * Project: YourProjectName
 * Created Date: Sunday, June 18th 2023, 6:04:42 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package PeerModel;

public enum PeerStatus {
    NotConnected,
    Connected,
    SentHandshake,
    RecivedHandShake,
    RecivedBitField
}
