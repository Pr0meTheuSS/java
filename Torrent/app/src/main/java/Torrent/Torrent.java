/*
 * Project: Torrent
 * Created Date: Wednesday, May 10th 2023, 12:42:23 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */

 package Torrent;

 import java.util.ArrayList;
 import java.util.List;
 
 public class Torrent {
     public String getName() {
         return name;
     }
 
     public void setName(String name) {
         this.name = name;
     }
 
     public byte[] getInfoHash() {
         return infoHash;
     }
 
     public void setInfoHash(byte[] infoHash) {
         this.infoHash = infoHash;
     }
 
     public List<byte[]> getPieces() {
         return pieces;
     }
     
     public void setPieces(List<byte[]> pieces) {
         this.pieces = pieces;
     }
 
     public int getPieceLength() {
         return pieceLength;
     }
 
     public void setPieceLength(int pieceLength) {
         this.pieceLength = pieceLength;
     }
 
     public long getFileLength() {
         return fileLength;
     }
 
     public void setFileLength(long fileLength) {
         this.fileLength = fileLength;
     }
 
     private long fileLength = 0;
     private String name = "";
     private int pieceLength = 0;
     private byte[] infoHash = new byte[20];
     private List<byte[]> pieces = new ArrayList<>();
 }