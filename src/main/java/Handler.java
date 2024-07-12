import java.io.*;

public class Handler {

    private static final String pathToFilesDirectory = "/home/nikita/Desktop/apps/codecrafters-http-server-java/tmp";

    public static final String CRLF = "\r\n";

    public Response handle(Request request, String directory) throws IOException {
        if (request.getPath().equals("/")) {
            return new Response(String.format("HTTP/1.1 200 OK%s%s", CRLF, CRLF));
        }

        if (request.getPath().startsWith("/echo")) {
            String param = request.getPath().substring(1).split("/")[1];

            return new Response(String.format("HTTP/1.1 200 OK%sContent-Type: text/plain%sContent-Length: %d%s%s%s", CRLF, CRLF, param.length(), CRLF, CRLF, param));
        }

        if (request.getPath().startsWith("/user-agent")) {
            String userAgent = request.getHeaders().get("User-Agent");

            return new Response(String.format("HTTP/1.1 200 OK%sContent-Type: text/plain%sContent-Length: %s%s%s%s", CRLF, CRLF, userAgent.length(), CRLF, CRLF, userAgent));
        }

        if (request.getPath().startsWith("/files")) {
            if(directory == null) {
                return new Response(String.format("HTTP/1.1 404 Not Found%s%s", CRLF, CRLF));
            }

            String fileName = request.getPath().substring(1).split("/")[1];

            File file = new File(String.format("%s%s", directory, fileName));

            if (!file.exists()) {
                return new Response(String.format("HTTP/1.1 404 Not Found%s%s", CRLF, CRLF));
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));

            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String content = sb.toString();
            long size = file.length();

            reader.close();

            return new Response(String.format("HTTP/1.1 200 OK%sContent-Type: application/octet-stream%sContent-Length: %s%s%s%s", CRLF, CRLF, size, CRLF, CRLF, content));
        }


        return new Response(String.format("HTTP/1.1 404 Not Found%s%s", CRLF, CRLF));
    }

}