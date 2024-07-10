import processors.HeadersProcessor;
import processors.Processor;
import processors.UrlProcessor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(4221);
            serverSocket.setReuseAddress(true);
            clientSocket = serverSocket.accept(); // Wait for connection from client.

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String line = in.readLine();

            String[] partsLine = line.split(" ");

            String path = partsLine[1];

            String[] split = path.substring(1).split("/");

            if (path.equals("/")) {
                clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            } else if (split[0].equals("echo") && split.length == 2) {
                String param = split[1];
                String response = String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s", param.length(), param);

                clientSocket.getOutputStream().write(response.getBytes());
            } else if (path.startsWith("/user-agent")) {
                in.readLine();

                String header = in.readLine();

                String[] keyValue = header.trim().split(": ");

                String value = keyValue[1];

                clientSocket.getOutputStream().write(String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %s\r\n\r\n%s", value.length(), value).getBytes());
            } else {
                clientSocket.getOutputStream().write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}


