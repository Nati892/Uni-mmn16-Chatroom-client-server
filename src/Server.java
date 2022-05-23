import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<ServersClientHandler> clients;
    private final int port = 8088;

    public Server() {
        clients = new ArrayList<ServersClientHandler>();
    }


    public void startServer() {
        System.out.println("starting server");//TODO
        ServerSocket sc = null;
        Socket s = null;
        try {
            sc = new ServerSocket(port);

            System.out.println(sc.getLocalPort()+"   " + sc.getInetAddress().getHostName());//TODO
            while (true) {
                s = sc.accept();
                ServersClientHandler Client = new ServersClientHandler(this, s);
                clients.add(Client);
                Client.start();
            }


        } catch (Exception e) {
            try {
                sc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("server closed");//TODO

    }

    public void publishMessage(String message, ServersClientHandler source) {
        for (ServersClientHandler client : clients
        ) {
            if (client != source)
                client.sendMessageToClient(message);
        }
    }

    public void removeClient(ServersClientHandler c) {
        if (clients.contains(c))
            clients.remove(c);

    }
}
