package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.Network.GameSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Controller Class for the server Client
 *
 * @author Jean
 */
public class Controller {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(Controller.class);

    @FXML // fx:id="closeButton"
    private Button closeButton; // Value injected by FXMLLoader

    @FXML // fx:id="portText"
    private Text portText; // Value injected by FXMLLoader

    @FXML // fx:id="ipText"
    private Text ipText; // Value injected by FXMLLoader

    @FXML // fx:id="connectBut"
    private Button connectBut; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
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
     *
     * @return host address
     */
    private String getIP() {
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
     *
     * @author Jean
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Event handler for the Connect button
     * Starts listening on the port 50000
     *
     * @author Jean
     */
    @FXML
    private void onConnect() {
        connectBut.setDisable(true);
        LOG.info("Looking for players!");
        try {
            ServerSocket servSock = new ServerSocket(50000);
            while (true) {
                try {
                    Socket player1 = servSock.accept();
                    LOG.info("Player found");
                    GameSession gs = new GameSession(player1);
                    Thread gameThread = new Thread(gs);
                    gameThread.start();
                    LOG.info("Game ended, searching for new player");
                } catch (Exception e) {
                    LOG.info(e.getMessage());
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

}
