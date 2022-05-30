import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class ClientIThreadio extends Thread {
    public static int CONNECTION_STATUS_FAILED = 0;
    public static int CONNECTION_STATUS_SUCCESS = 1;

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private int connectionStatus = CONNECTION_STATUS_FAILED;
    private Socket socket = null;
    private Clientable client;

    public ClientIThreadio(String serverIp, int serverPort, Clientable client) throws IOException {
        this.client = client;
        this.socket = socket = new Socket(serverIp, serverPort);
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if (socket.isConnected() && !socket.isClosed())
            connectionStatus = 1;
    }


    @Override
    public void run() {
        try {
            while (socket.isConnected() && !socket.isClosed()) {
                String msgFromChat = null;
                msgFromChat = bufferedReader.readLine();
                if (msgFromChat == null)
                    break;
                else {
                    String finalMsgFromChat = msgFromChat;
                    Platform.runLater(() -> {
                        client.receivedMessage(finalMsgFromChat);
                    });

                }
            }

        } catch (IOException e) {
        } finally {
            Platform.runLater(() -> {
                ConnectionLost();
            });
        }


    }

    public void SendMessage(String msg) throws IOException {
        if (socket.isConnected() && !socket.isClosed()) {
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }


    public int getConnectionStatus() {
        return this.connectionStatus;
    }

    private void ConnectionLost() {
        client.connectionLost();

        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
        }
    }
}
