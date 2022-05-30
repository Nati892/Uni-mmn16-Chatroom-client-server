import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private final int port = 8088;
    ServerWaitingRoom waitingRoom;

    public Server() {
        waitingRoom = new ServerWaitingRoom();
    }


    public void startServer() {
        System.out.println("starting server");//TODO
        ServerSocket sc = null;
        try {
            sc = new ServerSocket(port);
            System.out.println("Server up and running");
            while (true) {
                if (!sc.isClosed()) {
                    Socket s = sc.accept();
                    (new ClientThread(waitingRoom, s)).start();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            try {
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                System.out.println("Server closed");
            }
        }


    }


}
