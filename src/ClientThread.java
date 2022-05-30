import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    private String clientName;
    private ServerWaitingRoom waitingRoom;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;


    public ClientThread(ServerWaitingRoom waitingRoom, Socket s) {
        try {
            socket = s;
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.waitingRoom = waitingRoom;
            clientName = "";
            waitingRoom.addClient(this);
        } catch (IOException e) {
            System.out.println("Failed to get client connection");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        ListenToClient();
        closeClientIO();

    }

    public void ListenToClient() {
        String receivedMessage = "";
        try {
            sendMessageToClient("Please enter your name");//ask for name and send all current participants
            receivedMessage = bufferedReader.readLine();
            if (receivedMessage != null) {
                this.clientName = receivedMessage.trim();
                receiveMessageFromClient(receivedMessage + " has joined!");
                this.sendMessageToClient(waitingRoom.getParticipants(this));
            }

            while (socket.isConnected() && !socket.isClosed() && receivedMessage != null) {
                receivedMessage = bufferedReader.readLine();//listen for messages from client
                if (receivedMessage != null) {
                    receiveMessageFromClient(receivedMessage);
                }
            }
        } catch (Exception e) {
            System.out.println("client disconnected");
        } finally {
            killClient();
        }
    }

    private void receiveMessageFromClient(String message) {
        waitingRoom.publishMessage( clientName + ": " + message);
    }

    /**
     * sends a strin to the client
     * @param message String to be sent
     */
    public synchronized void sendMessageToClient(String message) {
        try {
            this.bufferedWriter.write(message);
            this.bufferedWriter.newLine();
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println("failed to send message");
        }
    }


    private void closeClientIO() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
        }

    }

    private void killClient() {
        System.out.println("client " + this.clientName + " was removed");
        waitingRoom.removeClient(this);
    }

    public String getClientName(){
        return this.clientName;
    }
}
