import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final HttpParser httpParser;

    private final Handler handler;

    private final Socket clientSocket;

    private String directory;

    public ConnectionHandler(Socket clientSocket) {
        this.httpParser = new HttpParser();
        this.handler = new Handler();
        this.clientSocket = clientSocket;
    }


    public ConnectionHandler(Socket clientSocket, String directory) {
        this.httpParser = new HttpParser();
        this.handler = new Handler();
        this.clientSocket = clientSocket;
        this.directory = directory;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            Request request = httpParser.parse(in);
            Response response = handler.handle(request, directory);

            outputStream.write(response.getMessageBytes());

            in.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
