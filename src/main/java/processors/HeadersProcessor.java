package processors;

import java.io.IOException;
import java.net.Socket;

public class HeadersProcessor implements Processor {
    private final Socket clientSocket;

    public HeadersProcessor(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public String process(String line) throws IOException {
        String[] headers = line.split(",");

        StringBuilder response = new StringBuilder();

        for (String header : headers) {
            String[] keyValue = header.trim().split(": ");

            String key = keyValue[0];
            String value = keyValue[1];

            System.out.println(key);
            if(key.equals("User-Agent")) {
                response.append(String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %s\r\n\r\n%s\r\n", value.length(), value));
            }
        }

        return response.toString();
    }
}
