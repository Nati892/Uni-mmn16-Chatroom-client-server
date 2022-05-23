import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RunClient {
    private static final int port = 8088;
    public static void main(String[] args) {
        Socket s = null;
        try {

            s = new Socket("127.0.0.1", port);
            new ClientIThreadio(s).start();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();


        }
    }
}
