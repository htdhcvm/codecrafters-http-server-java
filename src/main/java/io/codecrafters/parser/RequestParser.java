package io.codecrafters.parser;

import io.codecrafters.common.Method;
import io.codecrafters.common.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestParser implements Parser<Request, BufferedReader> {
    private static final Logger logger = Logger.getLogger(RequestParser.class.getName());

    @Override
    public Request parse(BufferedReader in) throws IOException {
        Request.RequestBuilder requestBuilder = Request.builder();

        requestLine(requestBuilder, in);
        requestHeaders(requestBuilder, in);
        requestBody(requestBuilder, in);

        var request = requestBuilder.build();

        return request;
    }


    private void requestLine(Request.RequestBuilder requestBuilder, BufferedReader in) throws IOException {
        String line = in.readLine();

        if (line.isEmpty()) return;

        String[] firstLine = line.split(" ");

        String method = firstLine[0];
        String path = firstLine[1];
        String httpVersion = firstLine[2];

        String hostLine = in.readLine();

        requestBuilder.method(Method.valueOf(method));
        requestBuilder.path(path);
        requestBuilder.httpVersion(httpVersion);

        if (hostLine.isEmpty()) return;

        String host = hostLine.split(": ")[1];
        requestBuilder.host(host);

    }

    private void requestHeaders(Request.RequestBuilder requestBuilder, BufferedReader in) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String header = in.readLine();

        while (!header.isEmpty()) {
            String[] split = header.split(":");

            String key = split[0].trim();
            String value = split[1].trim();

            headers.put(key, value);

            header = in.readLine();
        }

        requestBuilder.headers(headers);
    }

    private void requestBody(Request.RequestBuilder requestBuilder, BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();

        while (in.ready()) {
            sb.append((char) in.read());
        }

        String string = sb.toString();

        if(!string.isEmpty()) requestBuilder.body(string);
    }
}
