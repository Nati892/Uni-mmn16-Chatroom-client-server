import java.util.ArrayList;

public class ServerWaitingRoom {
    private ArrayList<ClientThread> waitingRoom;

    public ServerWaitingRoom() {
        this.waitingRoom = new ArrayList<ClientThread>();
    }


    public synchronized void publishMessage(ClientThread client, String message) {
        if (waitingRoom.size() > 1)
            for (ClientThread i : waitingRoom
            ) {
                i.sendMessageToClient(message);
            }

    }

    public synchronized void addClient(ClientThread client) {
        if (client != null) {
            if (waitingRoom.size() == 0)
                waitingRoom.add(client);
            else {
                for (ClientThread i : waitingRoom
                ) {
                    if (client.equals(i))
                        return;
                }
                waitingRoom.add(client);
            }
        }
    }


    public synchronized void removeClient(ClientThread client) {
        if (waitingRoom.contains(client))
            waitingRoom.remove(client);

    }

}
