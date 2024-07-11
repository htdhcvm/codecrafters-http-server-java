import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final Integer port;

    private final ServerSocket serverSocket;

    private ExecutorService threadPool;

    public Server(Integer port, Integer poolSize) throws IOException {
        this.threadPool = Executors.newFixedThreadPool(poolSize);

        this.serverSocket = new ServerSocket(port);

        this.port = port;
    }

    public void start() throws IOException {
        System.out.printf("Server listening port: %s%n", port);
        serverSocket.setReuseAddress(true);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Start handle request");
            threadPool.submit(new ConnectionHandler(clientSocket));
        }
    }
}
