/*
 * Project: Torrent
 * Created Date: Friday, May 2th 2023, 9:38:50 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package torrent;

import TorrentClientServer.TorrentClientServer;

public class App {
    public String getGreeting() {
        return "myTorrent run\n";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        if (args.length == 2) {
            TorrentClientServer torrentClientServer = new TorrentClientServer(args[0], args[1]);
            torrentClientServer.run();
        } else if (args.length == 1) {
            TorrentClientServer torrentClientServer = new TorrentClientServer("torrent.properties", args[1]);
            torrentClientServer.run();
        } else {
            System.out.println("Wrong args of cli - input in console: execfilename torrent.properties target_file.torrent");
        }
    }
}
