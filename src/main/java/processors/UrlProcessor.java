package processors;

import java.io.IOException;
import java.net.Socket;

public class UrlProcessor implements Processor {
    private final Socket clientSocket;

    public UrlProcessor(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void process(String line) throws IOException {
        System.out.println(line);
        String[] lineSplit = line.split(" ");

        String path = lineSplit[1];

        String[] split = path.substring(1).split("/");

        if(path.equals("/")) {
            clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
        } else if(path.startsWith("/echo") && split.length == 2) {
            String param = split[1];
            String response = String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s", param.length(), param);

            clientSocket.getOutputStream().write(response.getBytes());
        } else if(path.startsWith("/user-agent")) {
            clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n".getBytes());
            clientSocket.getOutputStream().write("Content-Type: text/plain\r\n".getBytes());
        } else {
            clientSocket.getOutputStream().write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
        }
    }
}
