

public class Handler {
    public static final String CRLF = "\r\n";

    public Response handle(Request request) {
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


        return new Response(String.format("HTTP/1.1 404 Not Found%s%s", CRLF, CRLF));
    }
}
