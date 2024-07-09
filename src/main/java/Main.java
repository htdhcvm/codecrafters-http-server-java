import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Logs from your program will appear here!");


        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(4221);
            serverSocket.setReuseAddress(true);
            clientSocket = serverSocket.accept(); // Wait for connection from client.
            System.out.println("accepted new connection");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String line = in.readLine();

            String[] partsLine = line.split(" ");
            System.out.println(Arrays.toString(partsLine));

            String path = partsLine[1];

            String[] split = path.substring(1).split("/");

            if (path.equals("/")) {
                clientSocket.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            }
            else if (split[0].equals("echo") && split.length == 2) {
                String param = split[1];
                String response = String.format("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s", param.length(), param);
                System.out.println(response);
                clientSocket.getOutputStream().write(response.getBytes());
            } else {
                clientSocket.getOutputStream().write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
