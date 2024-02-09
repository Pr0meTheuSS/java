/*
 * Project: Torrent
 * Created Date: Wednesday, May 10th 2023, 8:41:19 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */

package FileManager;

import Torrent.Torrent;
import java.util.*;

import Parser.Parser;

import java.io.*;
import java.nio.file.Path;

// Управляет путями работы с торрент файлами и оборачиваемыми файлами.
public class FileManager {
    public String getWorkingDirectory() {
        return workingDirPath;
    }

    // На самом деле это не set, а add, но над этим надо ещё подумать.
    public void setWorkingDirectoryName(String dirName) {
        workingDirPath = Path.of(workingDirPath, dirName).toString();
        new File(workingDirPath).mkdirs();
    }

    public Torrent getTorrentByHash(byte[] infoHash) {
        FilesManager fm = new FilesManager();
        File currDirFile = new File(workingDirPath);
        List<File> torrentFilesList = fm.torrentFilesList(currDirFile);
        Parser parser = new Parser();

        for (var torrentFile : torrentFilesList) {
            Torrent t = parser.parseTorrent(torrentFile.getName());
            if (t == null) {
                continue;
            }

            if (Arrays.equals(infoHash, t.getInfoHash())) {
                return t;
            }
        }

        return null;
    }

    public byte[] readFromFile(String name, int offset, int blockLength) throws IOException {
        try(RandomAccessFile file = new RandomAccessFile(Path.of(workingDirPath, name).toString(), "r")) {
            byte[] data = new byte[blockLength];
            file.seek(offset);
            file.read(data);
            return data;
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    public void writeToFile(String name, long offset, byte[] block) throws IOException {
        try(RandomAccessFile file = new RandomAccessFile(Path.of(workingDirPath, name).toString(), "rw")) {
            file.seek(offset);
            file.write(block);    
        }
    }

    protected String workingDirPath = System.getProperty("user.dir");
}

class FilesManager {
    public List<File> torrentFilesList(File dir) {
        List<File> torrentFiles = new ArrayList<>();
        for (File fileEntry : dir.listFiles()) {
            if (fileEntry.isDirectory()) {
                torrentFiles.addAll(0, torrentFilesList(fileEntry));
            } else if (fileEntry.getName().endsWith(".torrent")){
                torrentFiles.add(fileEntry);
            }
        }

        return torrentFiles;
    }
}
