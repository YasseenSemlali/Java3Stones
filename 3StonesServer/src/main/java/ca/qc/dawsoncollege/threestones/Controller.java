

package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.Network.GameSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

/**
 * Controller Class for the server Client
 * 
 * @author Jean
 */
public class Controller {
    
    private final static org.slf4j.Logger LOG =  LoggerFactory.getLogger(Controller.class);

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="closeButton"
    private Button closeButton; // Value injected by FXMLLoader

    @FXML // fx:id="portText"
    private Text portText; // Value injected by FXMLLoader

    @FXML // fx:id="ipText"
    private Text ipText; // Value injected by FXMLLoader

    @FXML // fx:id="connectBut"
    private Button connectBut; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
        void initialize() {
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'mainServerFXML.fxml'.";
        assert portText != null : "fx:id=\"portText\" was not injected: check your FXML file 'mainServerFXML.fxml'.";
        assert ipText != null : "fx:id=\"ipText\" was not injected: check your FXML file 'mainServerFXML.fxml'.";
        assert connectBut != null : "fx:id=\"connectBut\" was not injected: check your FXML file 'mainServerFXML.fxml'.";
        ipText.setText("IP Address: " + getIP()); 
    } 
    /**
     * get the server ip address
     * returns "Failed to get Address: if error occurs
     * @return 
     */    
    private String getIP(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG.error(e.getMessage());
            return "Failed to get address";
        }
    }
    /**
     * Event handler for the exit button
     * closes window
     * @param actionEvent (click)
     * @author Jean
     */
    @FXML
    private void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    /**
     * Event handler for the Connect button
     * Starts listening on the port 50000
     * @param actionEvent 
     * @author Jean
     */
    @FXML
    private void onConnect(ActionEvent actionEvent){
        connectBut.setDisable(true);
        try {
            LOG.info("Looking for players!");
            ServerSocket servSock = new ServerSocket(50000);
            while (true) {
                try(Socket player1 = servSock.accept()){
                    LOG.info("Player found");
                    GameSession gs = new GameSession(player1);
                    gs.run();
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
    
}
