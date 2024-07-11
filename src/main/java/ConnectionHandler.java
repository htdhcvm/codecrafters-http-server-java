import java.io.*;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Parser parser;

    private final Handler handler;

    private final Socket clientSocket;

    public ConnectionHandler(Socket clientSocket) {
        this.parser = new Parser();
        this.handler = new Handler();
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            Request request = parser.parse(in);
            Response response = handler.handle(request);

            outputStream.write(response.getMessageBytes());

            in.close();
            clientSocket.close();
            System.out.println("End handle request");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
