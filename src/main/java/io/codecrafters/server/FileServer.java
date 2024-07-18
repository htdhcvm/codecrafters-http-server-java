package io.codecrafters.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServer implements Server {
    private final Integer port;

    private final ServerSocket serverSocket;

    private final ExecutorService threadPool;

    private final String directory;

    public FileServer(Integer port, Integer poolSize, String directory) throws IOException {
        this.threadPool = Executors.newFixedThreadPool(poolSize);

        this.serverSocket = new ServerSocket(port);

        this.port = port;

        this.directory = directory;
    }

    @Override
    public void start() throws IOException {
        System.out.printf("Server listening port: %s%n", port);
        serverSocket.setReuseAddress(true);

        File file = new File(directory.substring(1));

        if(!file.exists()) file.mkdirs();

        while(true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ConnectionHandler(clientSocket, directory));
        }
    }
}
