package io.codecrafters.server;

import io.codecrafters.common.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Handler {

    public static final String CRLF = "\r\n";

    public Response handle(Request request, String directory) throws IOException {
        if (request.getPath().equals("/")) {
            return Response.builder().headers(String.format("HTTP/1.1 200 OK%s%s", CRLF, CRLF)).build();
        }

        if (request.getPath().startsWith("/echo") && request.getPath().substring(1).split("/").length > 1) {
            String param = request.getPath().substring(1).split("/")[1];
            String encodingKey = "Accept-Encoding";

            if (request.getHeaders().containsKey(encodingKey)) {
                String encodingValue = request.getHeaders().get(encodingKey);

                List<String> list = Arrays
                        .stream(encodingValue.split(","))
                        .map(String::trim)
                        .filter(EncodingHeaders::containsByValue)
                        .toList();

                if (!list.isEmpty()) {
                    byte[] byteArray = Gziper.griz(param);

                    return Response.builder().headers(String.format(
                            "HTTP/1.1 200 OK%sContent-Encoding: %s%sContent-Type: text/plain%sContent-Length: %s%s%s",
                            CRLF,
                            String.join(", ", list),
                            CRLF,
                            CRLF,
                            byteArray.length,
                            CRLF,
                            CRLF
                    )).bodyByte(byteArray).build();
                }
            }

            return Response
                    .builder()
                    .headers(String.format("HTTP/1.1 200 OK%sContent-Type: text/plain%sContent-Length: %d%s%s%s",
                                           CRLF,
                                           CRLF,
                                           param.length(),
                                           CRLF,
                                           CRLF,
                                           param
                    ))
                    .build();
        }

        if (request.getPath().startsWith("/user-agent")) {
            String userAgent = request.getHeaders().get("User-Agent");

            return Response
                    .builder()
                    .headers(String.format("HTTP/1.1 200 OK%sContent-Type: text/plain%sContent-Length: %s%s%s%s",
                                           CRLF,
                                           CRLF,
                                           userAgent.length(),
                                           CRLF,
                                           CRLF,
                                           userAgent
                    ))
                    .build();
        }

        if (request.getMethod().equals(Method.GET) && request.getPath().startsWith("/files")) {
            if (directory == null) {
                return Response.builder().headers(String.format("HTTP/1.1 404 Not Found%s%s", CRLF, CRLF)).build();
            }

            String fileName = request.getPath().substring(1).split("/")[1];

            File file = new File(String.format("%s%s", directory, fileName));

            if (!file.exists()) {
                return Response.builder().headers(String.format("HTTP/1.1 404 Not Found%s%s", CRLF, CRLF)).build();
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

            return Response
                    .builder()
                    .headers(String.format(
                            "HTTP/1.1 200 OK%sContent-Type: application/octet-stream%sContent-Length: %s%s%s%s",
                            CRLF,
                            CRLF,
                            size,
                            CRLF,
                            CRLF,
                            content
                    ))
                    .build();
        }

        if (request.getMethod().equals(Method.POST) && request.getPath().startsWith("/files")) {
            String fileName = directory + request.getPath().substring(1).split("/")[1];
            String body = request.getBody();

            System.out.println("fileName = " + fileName);
            File file = new File(fileName);

            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(body);
                fileWriter.close();
            }

            return Response.builder().headers(String.format("HTTP/1.1 201 Created%s%s", CRLF, CRLF)).build();
        }

        return Response.builder().headers(String.format("HTTP/1.1 404 Not Found%s%s", CRLF, CRLF)).build();
    }
}
