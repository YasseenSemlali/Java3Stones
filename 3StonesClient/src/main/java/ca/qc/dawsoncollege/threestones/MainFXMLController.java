package ca.qc.dawsoncollege.threestones;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

public class MainFXMLController {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(MainFXMLController.class);

    @FXML // fx:id="closeBut"
    private Button closeBut; // Value injected by FXMLLoader

    @FXML // fx:id="portInput"
    private TextField portInput; // Value injected by FXMLLoader

    @FXML // fx:id="ipInput"
    private TextField ipInput; // Value injected by FXMLLoader

    @FXML // fx:id="errorText"
    private Text errorText; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert closeBut != null : "fx:id=\"closeBut\" was not injected: check your FXML file 'mainFXML.fxml'.";
        assert portInput != null : "fx:id=\"portInput\" was not injected: check your FXML file 'mainFXML.fxml'.";
        assert ipInput != null : "fx:id=\"ipInput\" was not injected: check your FXML file 'mainFXML.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'mainFXML.fxml'.";
        LOG.info("INIZZSS");
    }

    /**
     * Makes sure the provided ip and port match the patern
     * starts game if the connection is established
     *
     * @author Jean
     */
    @FXML
    private void makeConnection() {
        try {
            LOG.info("Making Connection");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameFXML.fxml"));
            loader.load();
            Pattern ipPattern = Pattern.compile("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
            Pattern portPattern = Pattern.compile("^[0-9]{1,5}$"); //
            if (!ipPattern.matcher(ipInput.getText()).matches() || !portPattern.matcher(portInput.getText()).matches()) {
                errorText.setText("Invalid Input!");
                throw new NumberFormatException("Invalid input format");
            }
            GameFXMLController gameController = loader.getController();
            gameController.setConnection(ipInput.getText(), Integer.parseInt(portInput.getText()));
            AnchorPane root = loader.getRoot();
            Stage gameStage = new Stage();
            gameStage.setTitle("Three Stones");
            gameStage.setScene(new Scene(root));
            closeWindow();
            gameStage.show();
            root.requestFocus();
        } catch (IOException | NumberFormatException e) {
            errorText.setText("Invalid Input!");
            LOG.error("ERROR LOADING PAGE: " + e);
        }
    }

    /**
     * Event handler for exit button
     * closes window on click
     *
     * @author Jean
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeBut.getScene().getWindow();
        stage.close();
    }
}