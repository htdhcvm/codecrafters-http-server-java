package processors;

import java.io.IOException;
import java.net.Socket;

public class UrlProcessor implements Processor {
    private final Socket clientSocket;

    public UrlProcessor(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public String process(String line) throws IOException {
        String[] lineSplit = line.split(" ");

        String path = lineSplit[1];

        String[] split = path.substring(1).split("/");

        if (path.equals("/")) {
            return "HTTP/1.1 200 OK\r\n\r\n";
        } else if (path.startsWith("/echo") && split.length == 2) {
            String param = split[1];

            return String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s", param.length(), param);
        } else if (path.startsWith("/user-agent")) {
            return "";
        } else {
            return "HTTP/1.1 404 Not Found\r\n\r\n";
        }
    }
}
