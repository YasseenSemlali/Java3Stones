package ca.qc.dawsoncollege.threestones;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Main app for the Server Client
 * launches the app
 *
 * @author Jean
 */
public class Main extends Application {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method, launches the app
     *
     * @param primaryStage the main stage
     * @throws Exception throws exception if stage cannot be loaded
     * @author Jean
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainServerFXML.fxml"));
            GridPane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Server Client");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | IllegalStateException ex) {
            LOG.error("Cannot load scene", ex);
        }
    }
}
