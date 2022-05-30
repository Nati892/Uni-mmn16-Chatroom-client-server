import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ClientPanelController {

    @FXML
    private Button BTNConnect;

    @FXML
    private Button BTNSendMessage;

    @FXML
    private VBox MessagesBox;

    @FXML
    private TextField TFMessage;

    @FXML
    private TextField TFServerIp;

    @FXML
    private TextField TFServerPort;

}
