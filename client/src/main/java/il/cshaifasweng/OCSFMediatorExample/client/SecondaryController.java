/**
 * Sample Skeleton for 'secondary.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;
import static il.cshaifasweng.OCSFMediatorExample.client.SimpleClient.client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SecondaryController {

    @FXML // fx:id="hostInput"
    private TextField hostInput; // Value injected by FXMLLoader

    @FXML // fx:id="hostLabel"
    private Label hostLabel; // Value injected by FXMLLoader

    @FXML // fx:id="okButton"
    private Button okButton; // Value injected by FXMLLoader

    @FXML // fx:id="portInput"
    private TextField portInput; // Value injected by FXMLLoader

    @FXML // fx:id="portLabel"
    private Label portLabel; // Value injected by FXMLLoader

    @FXML
    void onOKButton(ActionEvent event) throws Exception {
        System.out.println("OK");
        if(portInput.getText().isEmpty() || hostInput.getText().isEmpty()) {
            SimpleClient.port=3000;
            SimpleClient.host="localhost";
        }
        else {
            SimpleClient.port= Integer.parseInt(portInput.getText());
            SimpleClient.host=hostInput.getText();
        }
        client = SimpleClient.getClient();
        try {
            client.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            App.switchToPrimary();
            SimpleClient.getClient().sendToServer("add client");
            App.secondaryStage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
