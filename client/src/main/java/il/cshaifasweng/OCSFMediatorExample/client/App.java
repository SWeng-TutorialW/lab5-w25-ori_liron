package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    public static Stage secondaryStage;
    private SimpleClient client;
    private PrimaryController controller;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage=stage;
    	EventBus.getDefault().register(this);
        this.controller = new PrimaryController();
//    	client = SimpleClient.getClient();
//    	client.openConnection();
      scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    @Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
    	EventBus.getDefault().unregister(this);
        client.sendToServer("remove client");
        client.closeConnection();
		super.stop();
	}
    
    @Subscribe
    public void onWarningEvent(WarningEvent event) {
    	Platform.runLater(() -> {
    		Alert alert = new Alert(AlertType.WARNING,
        			String.format("Message: %s\nTimestamp: %s\n",
        					event.getWarning().getMessage(),
        					event.getWarning().getTime().toString())
        	);
            System.out.println("in App sub  Warning event");
        	alert.show();
    	});
    	
    }
    @Subscribe
    public void onWinEvent(WinEvent event) throws IOException {
        Platform.runLater(() -> {
        System.out.println("in App winEvent");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game over");
            alert.setHeaderText(null);
            alert.setContentText(event.player+" has won the game");
            alert.show();
        });
    }
    public static void switchToPrimary() throws Exception {
        Parent root = FXMLLoader.load(App.class.getResource("primary.fxml"));
        primaryStage.setScene(new Scene(root));
    }
	public static void main(String[] args) {
        launch();
    }

}