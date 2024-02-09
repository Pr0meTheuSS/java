/*
 * Project: Torrent
 * Created Date: Tuesday, May 23th 2023, 8:49:33 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package TorrentClientServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import TorrentClient.TorrentClient;
import TorrentServer.TorrentServer;

public class TorrentClientServer implements Runnable {

    public TorrentClientServer(String propertiesFilename, String targetTorrentFilename) {
        setConfigurations(propertiesFilename, targetTorrentFilename);
    }

    public String getServerWorkingDirectory() {
        return server.getWorkingDirectory();
    }

    public String getClientWorkingDirectory() {
        return client.getWorkingDirectory();
    }

    public void run() {
        Thread serverThread = new Thread(server);

        Thread clientThread = new Thread(client);

        try {
            clientThread.start();
            serverThread.start();
            clientThread.join();
            serverThread.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void setConfigurations(String propertiesFilename, String targetTorrentFilename) {
        Properties properties = new Properties();
        try(InputStream input = new FileInputStream(new File(propertiesFilename))) {
            properties.load(input);

            int serverSocketTimeout = Integer.parseInt(properties.getProperty("ServerSocketTimeout"));
            int clientSocketTimeout = Integer.parseInt(properties.getProperty("ClientSocketTimeout"));
            int portToListen = Integer.parseInt(properties.getProperty("PortToListen"));

            server = new TorrentServer(portToListen, serverSocketTimeout, clientSocketTimeout);
            client = new TorrentClient(targetTorrentFilename);

            String serverWorkingDirectory = properties.getProperty("ServerWorkingDirectory");
            String clientWorkingDirectory = properties.getProperty("ClientWorkingDirectory");
            server.setWorkingDirectoryName(serverWorkingDirectory);
            client.setWorkingDirectoryName(clientWorkingDirectory);
            for (var key : properties.keySet()) {
                if (key.toString().startsWith("peer")) {
                    var peerData = properties.get(key).toString().split(":", 2);
                    client.addPeer(peerData[0], Integer.parseInt(peerData[1]));
                }
            }
        } catch( IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected TorrentServer server = null;
    protected TorrentClient client = null;
}
