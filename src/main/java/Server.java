import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(Integer port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setReuseAddress(true);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }
}
