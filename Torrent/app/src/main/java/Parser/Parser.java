/*
 * Project: Torrent
 * Created Date: Sunday, May 7th 2023, 10:29:36 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package Parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

import Torrent.Torrent;
import Hasher.Hasher;

public class Parser {
    public Torrent parseTorrent(String fileName) {
        try (InputStream sc = getClass().getResourceAsStream("/" + fileName)) {
            if (sc == null) {
                return null;
            }
            Bencode bencode = new Bencode(StandardCharsets.ISO_8859_1);
            torrentMap = bencode.decode(sc.readAllBytes(), Type.DICTIONARY);

            Torrent torrent = new Torrent();
            torrent.setName(fileName);
            torrent.setInfoHash(calcInfoHash());
            torrent.setName(parseName());
            torrent.setPieces(parsePieces());
            torrent.setPieceLength(parsePieceLength());
            torrent.setFileLength(parseFileLength());

            return torrent;
        } catch(IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }   

    private long parseFileLength() {
        return Long.parseLong(getTorrentInfoMap().get("length").toString());
    }

    private Map<String, Object> getTorrentInfoMap() {
        if (torrentInfoMap == null) {
            torrentInfoMap = (Map<String, Object>)torrentMap.get("info");
        }

        return torrentInfoMap;

    }

    private byte[] calcInfoHash() {
        Bencode bc = new Bencode(StandardCharsets.ISO_8859_1);
        var code = bc.encode(getTorrentInfoMap());
        return Hasher.getSHA1(code);
    }

    private String parseName() {
        return getTorrentInfoMap().get("name").toString();
    }

    private List<byte[]> parsePieces() {
        List<byte[]> pieces = new ArrayList<>();
        var rawPieces =  getTorrentInfoMap().get("pieces").toString().getBytes(StandardCharsets.ISO_8859_1);

        piecesCount = rawPieces.length / sha1Size;

        for (int i = 0; i < piecesCount; i++) {
            byte[] p = new byte[sha1Size];
            System.arraycopy(rawPieces, i * sha1Size, p, 0, sha1Size);
            pieces.add(p);
        }

        return pieces;
    }

    private int parsePieceLength() {
        pieceLength =  Integer.parseInt(getTorrentInfoMap().get("piece length").toString());
        return pieceLength;
    }

    private Map<String, Object> torrentMap = null;
    private Map<String, Object> torrentInfoMap = null;
    private final int sha1Size = 20;
    private int pieceLength = 0;
    private int piecesCount = 0;
}