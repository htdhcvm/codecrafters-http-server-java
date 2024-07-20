package io.codecrafters.server;

import io.codecrafters.common.Request;
import io.codecrafters.common.Response;
import io.codecrafters.parser.RequestParser;

import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final RequestParser requestParser;

    private final Handler handler;

    private final Socket clientSocket;

    private String directory;

    public ConnectionHandler(Socket clientSocket) {
        this.requestParser = new RequestParser();
        this.handler = new Handler();
        this.clientSocket = clientSocket;
    }


    public ConnectionHandler(Socket clientSocket, String directory) {
        this.requestParser = new RequestParser();
        this.handler = new Handler();
        this.clientSocket = clientSocket;
        this.directory = directory;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            Request request = requestParser.parse(in);
            Response response = handler.handle(request, directory);

            outputStream.write(response.getHeadersBytes());

            if(response.getBodyByte().length > 0) {
                outputStream.write(response.getBodyByte());
            }

            in.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
