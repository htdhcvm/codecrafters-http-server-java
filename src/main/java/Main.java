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
        Server server = new Server(4221);
        Socket clientSocket = server.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Map<Integer, Processor> map = Map.of(0, new UrlProcessor(clientSocket), 3, new HeadersProcessor(clientSocket));

        String line = in.readLine();
        int i = 0;

        while (!line.isEmpty()) {
            System.out.println("line = " + line);

            if (map.containsKey(i)) {
                map.get(i).process(line);
            }

            line = in.readLine();
            i++;
        }
    }
}


