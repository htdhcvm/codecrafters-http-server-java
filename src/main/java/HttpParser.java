import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpParser implements Parser<Request, BufferedReader> {

    @Override
    public Request parse(BufferedReader in) throws IOException {
        String line = in.readLine();

        if (line.isEmpty()) return null;

        String[] firstLine = line.split(" ");

        String method = firstLine[0];
        String path = firstLine[1];
        String httpVersion = firstLine[2];

        String hostLine = in.readLine();
        if (hostLine.isEmpty()) return new Request(method, path, httpVersion);

        String host = hostLine.split(": ")[1];

        Map<String, String> headers = new HashMap<>();
        String header = in.readLine();

        while (!header.isEmpty()) {

            String[] split = header.split(":");

            String key = split[0].trim();
            String value = split[1].trim();

            headers.put(key, value);

            header = in.readLine();
        }

        return new Request(method, path, host, httpVersion, headers);
    }
}
