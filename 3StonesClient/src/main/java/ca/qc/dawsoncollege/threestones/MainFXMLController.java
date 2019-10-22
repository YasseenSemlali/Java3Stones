/**
 * Sample Skeleton for 'mainFXML.fxml' Controller Class
 */

package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.GameController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

public class MainFXMLController {

    private final static org.slf4j.Logger LOG =  LoggerFactory.getLogger(Main.class);

    @FXML // fx:id="closeBut"
    private Button closeBut; // Value injected by FXMLLoader

    @FXML // fx:id="portInput"
    private TextField portInput; // Value injected by FXMLLoader

    @FXML // fx:id="ipInput"
    private TextField ipInput; // Value injected by FXMLLoader

    @FXML // fx:id="errorText"
    private Text errorText; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert closeBut != null : "fx:id=\"closeBut\" was not injected: check your FXML file 'mainFXML.fxml'.";
        assert portInput != null : "fx:id=\"portInput\" was not injected: check your FXML file 'mainFXML.fxml'.";
        assert ipInput != null : "fx:id=\"ipInput\" was not injected: check your FXML file 'mainFXML.fxml'.";
        assert errorText != null : "fx:id=\"errorText\" was not injected: check your FXML file 'mainFXML.fxml'.";
        LOG.info("INIZZSS");
    }
    /**
     * Connect button event handler 
     * validates the input and calls makeConnection()
     * @param actionEvent 
     * @author Jean 
     */
    @FXML 
    private void onConnect(ActionEvent actionEvent){
        try{
            if(!portInput.getText().isEmpty() && !ipInput.getText().isEmpty() && !portInput.getText().equals(" ") && !ipInput.getText().equals(" ")){
                errorText.setText("");
                makeConnection();
            }
            else{
                errorText.setText("Invalid Input!");
            }
        }
        catch(Exception e){
            LOG.info(e.getMessage());
        }
    }
    /**
     * Makes sure the provided ip and port match the patern
     * starts game if the connection is established
     * @author Jean
     */
    @FXML
    private void makeConnection(){
        try {            
            LOG.info("Making Connection");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameFXML.fxml"));
            loader.load();
            Pattern ipPattern = Pattern.compile("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
            Pattern portPattern = Pattern.compile("^[0-9]{1,5}$"); //
            if(!ipPattern.matcher(ipInput.getText()).matches() || !portPattern.matcher(portInput.getText()).matches()){
                errorText.setText("Invalid Input!");
                throw new NumberFormatException("Invalid input format"); 
            }
            GameFXMLController gameController = loader.getController();
            gameController.setConnection(ipInput.getText(),Integer.parseInt(portInput.getText()));
            AnchorPane root = loader.getRoot();
            Stage gameStage = new Stage();
            gameStage.setTitle("Three Stones");
            gameStage.setScene(new Scene(root));
            closeWindow();
            gameStage.show();
        } catch (IOException | NumberFormatException e) {
            errorText.setText("Invalid Input!");
            LOG.error("ERROR LOADING PAGE: " + e);
        }
    }
    /**
     * Event handler for exit button
     * closes window on click
     * @param actionEvent (click)
     * @author Jean
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeBut.getScene().getWindow();
        stage.close();
    }
}