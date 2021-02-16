import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleChatServer {
    private static List<ClientHandler> clientsList = new ArrayList<>();

    public void go() {
        try (ServerSocket serverSock = new ServerSocket(5000)){
            System.out.println("server ready for work");
            Counter counter = new Counter();

            while (true) {
                Socket clientSocket = serverSock.accept();
                try {
                    clientsList.add(new ClientHandler(clientSocket, counter));

                } catch (Exception e) {
                    clientSocket.close();
                }
                System.out.println("got a connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new SimpleChatServer().go();
    }
}
