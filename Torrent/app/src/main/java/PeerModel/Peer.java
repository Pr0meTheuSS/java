/*
 * Project: YourProjectName
 * Created Date: Sunday, June 18th 2023, 6:03:44 pm
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

import java.util.BitSet;

public class Peer {
    public Peer(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public PeerStatus getStatus() {
        return status;
    }

    public void setStatus(PeerStatus status) {
        this.status = status;
    }

    public BitSet getBitField() {
        return bitField;
    }

    public void setBitField(BitSet bs) {
        bitField = bs;
    }

    private String ipAddress;
    private int port;
    private PeerStatus status = PeerStatus.NotConnected;
    private BitSet bitField = new BitSet();
}





