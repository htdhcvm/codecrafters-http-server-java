import java.util.Map;

public class Request {
    private String method;

    private String path;

    private String httpVersion;

    private String host;

    private Map<String, String> headers;

    public Request(String method, String path, String httpVersion) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
    }


    public Request(String method, String path, String host, String httpVersion, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.host = host;
        this.httpVersion = httpVersion;
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                ", host='" + host + '\'' +
                ", headers=" + headers +
                '}';
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getHost() {
        return host;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
