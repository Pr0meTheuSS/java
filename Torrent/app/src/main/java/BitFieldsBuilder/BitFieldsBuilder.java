/*
 * Project: Torrent
 * Created Date: Saturday, May 13th 2023, 4:19:38 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */

package BitFieldsBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.BitSet;

import FileManager.FileManager;
import Hasher.Hasher;
import Torrent.Torrent;

public class BitFieldsBuilder {

    public static BitSet getBitField(Torrent torrent, FileManager fm) {
        BitSet bitFields = new BitSet(torrent.getPieces().size());
        try (InputStream sc =  new FileInputStream(Path.of(fm.getWorkingDirectory(), torrent.getName()).toFile())) {
            byte[] D = new byte[torrent.getPieceLength()];
            for (int i = 0; i < torrent.getPieces().size(); i++) {
                D = sc.readNBytes(torrent.getPieceLength());
                var pHash = Hasher.getSHA1(D);
                bitFields.set(i, Arrays.equals(pHash, torrent.getPieces().get(i)));
            }
            return bitFields;    
        } catch (FileNotFoundException e) {
            return bitFields;
        } catch (IOException e) {
            return null;
        }
    }
}
