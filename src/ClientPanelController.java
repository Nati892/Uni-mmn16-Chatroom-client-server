import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class ClientPanelController implements clientCallbacks {
    private ClientIThreadIO clientIO;

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
        //disable chatRoom functionality
        TFMessage.setDisable(true);
        BTNSendMessage.setDisable(true);
    }

    @FXML
    void BTNConnectOnClick(ActionEvent event) {
        if (BTNConnect.getText().equals("Connect")) {//if not connected then attempt to connect
            try {
                if (TFServerIp.getText().trim().length() > 0 && TFServerPort.getText().trim().length() > 0) {//only attempt to connect if address and port textFields are non empty
                    this.clientIO = new ClientIThreadIO(TFServerIp.getText().trim(), Integer.parseInt(TFServerPort.getText().trim()), this);
                }
            } catch (Exception e) {
            } finally {
                if (clientIO != null)
                    if (clientIO.getConnectionStatus() == ClientIThreadIO.CONNECTION_STATUS_FAILED) {
                        this.clientIO = null;
                    } else {
                        clientIO.start();
                    }
            }

            if (clientIO != null) {//connection successful, allows sending messages and disconnection
                BTNConnect.setText("Disconnect");
                TFMessage.setDisable(false);
                BTNSendMessage.setDisable(false);
            }
        } else {
            if (clientIO != null) {
                clientIO.Disconnect();
        }
    }}

    @FXML
    void BTNSendMessageOnClick(ActionEvent event) {
        if (clientIO != null && TFMessage.getText().trim().length() > 0) {//if message TF is non-empty then attempt to send message
            try {
                clientIO.SendMessage(TFMessage.getText().trim());
            } catch (Exception e) {
                MessagesBox.getItems().add("Local: Failed to send message!");
            }
        }
    }


    @Override
    public void connectionLost() {
        if (clientIO != null) {
            clientIO.Disconnect();
            clientIO = null;
        }
        MessagesBox.getItems().add("Connection closed");
        BTNConnect.setText("Connect");
        TFMessage.setDisable(true);
        BTNSendMessage.setDisable(true);
    }

    @Override
    public void receivedMessage(String msg) {
        MessagesBox.getItems().add(msg);
        MessagesBox.scrollTo(MessagesBox.getItems().size() - 1);
    }
}
