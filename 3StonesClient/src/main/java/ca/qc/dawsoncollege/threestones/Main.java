package ca.qc.dawsoncollege.threestones;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main app for the client server
 *
 * @author Jean
 */
public class Main extends Application {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Loads the FXML file and launches the main app for the Game
     *
     * @param primaryStage main stage
     * @throws Exception throws exception if cannot load stage
     * @author Jean
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainFXML.fxml"));
            GridPane root = loader.load();
            MainFXMLController controller = loader.getController();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Three Stones");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | IllegalStateException ex) {
            LOG.error("Cannot load scene", ex);
        }
    }
}
