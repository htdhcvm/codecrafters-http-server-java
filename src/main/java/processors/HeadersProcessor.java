package processors;

import java.io.IOException;
import java.net.Socket;

public class HeadersProcessor implements Processor {
    private final Socket clientSocket;

    public HeadersProcessor(Socket socket) {
        clientSocket = socket;
    }

    @Override
    public void process(String line) throws IOException {
        String[] headers = line.split(",");

        for (String header : headers) {
            String[] keyValue = header.trim().split(": ");

            String key = keyValue[0];
            String value = keyValue[1];

            if(key.equals("User-Agent")) {
                String response = String.format("Content-Length: %d\r\n\r\n%s", value.length(), value);
                clientSocket.getOutputStream().write(response.getBytes());
            }
        }
    }
}
