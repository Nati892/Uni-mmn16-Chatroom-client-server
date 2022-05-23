import java.io.*;
import java.net.Socket;

public class ServersClientHandler extends Thread {

    private String clientName;
    private Server server;
    private Socket connection;


    public ServersClientHandler(Server server, Socket s) {
        this.server = server;
        connection = s;
        clientName = null;
    }

    @Override
    public void run() {
        super.run();
        ListenToClient();
        killClient();
    }

    public void ListenToClient() {


        boolean isConnectionGood = true;
        while (isConnectionGood) {
            try {
                System.out.println(connection.getInputStream().available());
                ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
                NetMessage message;
                message=(NetMessage) ois.readObject();


            } catch (Exception e) {
                System.out.println("client disconnected");
                isConnectionGood = false;
                e.printStackTrace();
            }

            if (!connection.isConnected())
                isConnectionGood = false;
        }

    }

    public void receiveMessageFromClient(String message) {
        System.out.println("got from client : " + message);
        server.publishMessage(message, this);
    }




    public void sendMessageToClient(String message) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(new NetMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void killClient() {
        System.out.println("killed client");
        server.removeClient(this);
    }
}
