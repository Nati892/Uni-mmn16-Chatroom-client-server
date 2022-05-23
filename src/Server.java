import java.util.ArrayList;

public class Server {

    private ArrayList<ServersClientHandler> clients;
    private final int port = 8080;

    public Server() {
        clients = new ArrayList<ServersClientHandler>();
    }


    public void startServer() {
        //TODO start server



        while (true) {
            //wait for client and add him


        }
    }

    public void publishMessage(String message, ServersClientHandler source) {
        for (ServersClientHandler client : clients
        ) {
            if (client != source)
                client.sendMessageToClient(message);
        }
    }
}
