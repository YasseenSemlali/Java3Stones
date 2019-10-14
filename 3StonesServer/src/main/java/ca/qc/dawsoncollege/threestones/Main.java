package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Game;
import ca.qc.dawsoncollege.threestones.game.Network.GameSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;


public class Main extends Application {
private final static org.slf4j.Logger LOG =  LoggerFactory.getLogger(Main.class);
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainServerFXML.fxml"));           
            GridPane root = loader.load();
            Controller controller = loader.getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Server Client");
            primaryStage.setScene(scene);
            primaryStage.show();
            }
        catch (IOException | IllegalStateException ex) {
            LOG.error("Cannot load scene", ex);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
