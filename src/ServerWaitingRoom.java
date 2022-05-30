import java.util.ArrayList;

public class ServerWaitingRoom {
    private ArrayList<ClientThread> waitingRoom;

    public ServerWaitingRoom() {
        this.waitingRoom = new ArrayList<ClientThread>();//holds all clients
    }

    /**
     * sends message to all participants in waiting roo,
     * @param message String to be published
     */
    public synchronized void publishMessage(String message) {
        if (waitingRoom.size() > 1)
            for (ClientThread i : waitingRoom
            ) {
                i.sendMessageToClient(message);
            }
    }

    /**
     * adds a new client to this chatroom
     * @param client the client to be added
     */
    public synchronized void addClient(ClientThread client) {
        if (client != null) {
            if (waitingRoom.size() == 0)
                waitingRoom.add(client);
            else {
                //only adds client if unique
                for (ClientThread i : waitingRoom
                ) {
                    if (client.equals(i))
                        return;
                }
                waitingRoom.add(client);
            }
        }
    }

    /**
     * removes client from chat room
     * @param client to bre removed
     */
    public synchronized void removeClient(ClientThread client) {
        if (waitingRoom.contains(client))
            waitingRoom.remove(client);

    }

    /**
     * returns  a list of all current participants
     * @param client the client to send the list to
     * @return String of all userNames
     */
    public synchronized String getParticipants(ClientThread client) {
        String partList = "Current participants: ";
        for (ClientThread c : this.waitingRoom
        ) {
            if (!c.equals(client))
                partList += c.getClientName() + ", ";
        }
        partList += "You\n";
        return partList;
    }

}
