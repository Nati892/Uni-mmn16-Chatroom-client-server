import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class ClientPanelController implements Clientable {
    private ClientIThreadio clientIO;

    @FXML
    private Label LabelConnectionStatus;

    @FXML
    private Button BTNConnect;

    @FXML
    private Button BTNSendMessage;

    @FXML
    private ListView<String> MessagesBox;

    @FXML
    private TextField TFMessage;

    @FXML
    private TextField TFServerIp;

    @FXML
    private TextField TFServerPort;

    @FXML
    void initialize() {

        TFMessage.setDisable(true);
        BTNSendMessage.setDisable(true);
    }

    @FXML
    void BTNConnectOnClick(ActionEvent event) {
        if (BTNConnect.getText().equals("Connect")) {
            try {
                if (TFServerIp.getText().trim().length() > 0 && TFServerPort.getText().trim().length() > 0) {
                    System.out.println("attempting");//TODO del line
                    this.clientIO = new ClientIThreadio(TFServerIp.getText().trim(), Integer.parseInt(TFServerPort.getText().trim()), this);

                }
            } catch (Exception e) {
            } finally {
                if (clientIO != null)
                    if (clientIO.getConnectionStatus() == ClientIThreadio.CONNECTION_STATUS_FAILED) {
                        this.clientIO = null;
                    } else {
                        clientIO.start();
                    }
            }

            if (clientIO != null) {
                BTNConnect.setText("Disconnect");
                TFMessage.setDisable(false);
                BTNSendMessage.setDisable(false);
            } else {
                //TODO connection failed
            }
        } else {
            connectionLost();
            BTNConnect.setText("Connect");
        }
    }

    @FXML
    void BTNSendMessageOnClick(ActionEvent event) {
        if (clientIO != null && TFMessage.getText().trim().length() > 0) {
            try {


                clientIO.SendMessage(TFMessage.getText().trim());
            } catch (Exception e) {
            }
        }
    }


    @Override
    public void connectionLost() {
        if (clientIO != null) {
            clientIO.Disconnect();
            clientIO = null;
        }
        TFMessage.setDisable(true);
        BTNSendMessage.setDisable(true);
    }

    @Override
    public void receivedMessage(String msg) {
        System.out.println(msg);
        MessagesBox.getItems().add(msg);
        MessagesBox.scrollTo(MessagesBox.getItems().size() - 1);
    }
}
