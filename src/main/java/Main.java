
import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server(4221, 10);

        server.start();
    }
}

