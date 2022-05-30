import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientIThreadio extends Thread {
    private Socket connection=null;

    public ClientIThreadio(Socket connection) {
        this.connection = connection;
    }


    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(connection.getOutputStream());
            System.out.println("writing");
            oos.writeObject(new NetMessage("hiiiiii"));
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
