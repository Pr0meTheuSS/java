/*
 * Project: Torrent
 * Created Date: Saturday, May 13th 2023, 2:48:14 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */

package Hasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    static public byte[] getSHA1(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return md.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}




