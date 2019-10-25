package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.Network.PacketInfo;
import ca.qc.dawsoncollege.threestones.game.Network.ThreeStonesConnector;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Game FXML controller
 *
 * @author Jean Naima
 */
public class GameFXMLController {

    private final static Logger LOG = LoggerFactory.getLogger(GameFXMLController.class);
    private ThreeStonesConnector connection;
    private Board board;
    private Node lastMove;
    private int numPieces;
    private boolean isClosed = false;

    @FXML // fx:id="gridPane"
    private GridPane gridPane; // Value injected by FXMLLoader

    @FXML
    private Text infoText;

    @FXML
    private Text clientScore;

    @FXML
    private Text serverScore;

    @FXML
    private Text winner;

    @FXML
    private Text piecesRemaining;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        // TODO
        addEventGrid();
        board = new Board();
        numPieces = 15;
    }

    /***
     * Method that add events to all cells withing grid
     * @author Jean Naima
     */
    private void addEventGrid() {
        ObservableList<Node> children = gridPane.getChildren();
        LOG.info("Events added to grid");
        for (Node node : children) {
            node.setId("circle");
            if (node instanceof Circle) {
                node.setOnMouseClicked(null);
                node.setOnMouseClicked(e -> {
                    try {
                        clientMove(e);
                    } catch (IOException ex) {
                        LOG.info("Connection with Server lost!");
                        removeEvents();
                        infoText.setText("Connection with Server lost!");
                    }
                });
            }
        }
    }

    /**
     * Set Connection to connect so server
     *
     * @param ip   server ip input in mainStage
     * @param port server port input in mainStage
     * @throws IOException if connection cannot be made
     * @author Jean Naima
     */
    public void setConnection(String ip, int port) throws IOException {
        connection = new ThreeStonesConnector(ip, port);
    }

    /**
     * Method to start new game
     * Initializes board and player
     *
     * @throws IOException if packet is not sent properly
     * @author Jean Naima
     */
    public void startGame() throws IOException {
        if (!isClosed) {
            lastMove = null;
            numPieces = 15;
            board = new Board();
            connection.sendData(PacketInfo.NEW_GAME, PacketInfo.PLAYER_ONE, (byte) 1, (byte) 1);
            addEventGrid();
            infoText.setText("");
            winner.setText("");
            clientScore.setText("You: 0");
            serverScore.setText("Opponent: 0");
            piecesRemaining.setText("Pieces Remaining: 15");
            LOG.info("New Game Started");
        }
    }

    /**
     * Checks winner based on current score
     *
     * @param clientScore the clients current score
     * @param serverScore the servers current score
     * @author Jean Naima
     */
    private void checkWinner(byte clientScore, byte serverScore) {
        if (clientScore == serverScore) {
            winner.setText("You tied !");
        } else if (clientScore > serverScore) {
            winner.setText("You Won !");
        } else {
            winner.setText("You Lost !");
        }
    }

    /**
     * gets move made by server
     *
     * @throws IOException if the receiving data has an exception
     * @author Saad
     */
    private void processReceivedData() throws IOException {
        byte[] data = connection.receiveData();
        //data[0]: move message type
        //data[1]: player's turn
        //data[2]: move location
        //data[3]:'move location
        switch (data[0]) {
            case PacketInfo.MOVE:
                board.addMove(data[2], data[3], data[1]);
                movePlayedServer(data[2], data[3]);
                clientScore.setText("You: " + data[4]);
                serverScore.setText("Opponent: " + data[5]);
                break;
            case PacketInfo.QUIT:
                LOG.info("Last move");
                infoText.setText("Game Over!");
                clientScore.setText("You: " + data[4]);
                serverScore.setText("Opponent: " + data[5]);
                checkWinner(data[4], data[5]);
                board.addMove(data[2], data[3], data[1]);
                movePlayedServer(data[2], data[3]);
                numPieces = 0;
                break;
            default:
                LOG.info("No Data Received From Server");

        }
    }

    /**
     * Handles the GUI part of the move made by the client
     *
     * @param x x position of the move
     * @param y y position of the move
     * @author Jean Naima
     */
    private void movePlayedClient(int x, int y) {
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) {
                if (lastMove == null) {
                    lastMove = node;
                    node.setId("lastClickedW");
                } else {
                    lastMove.setId("clickedB");
                    lastMove = node;
                    node.setId("lastClickedW");
                }
                break;
            }
        }
    }

    /**
     * Handles the GUI part of the move made by the server
     *
     * @param x x position of the move
     * @param y y position of the move
     * @author Jean Naima
     */
    private void movePlayedServer(byte x, byte y) {
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) {
                if (lastMove == null) {
                    lastMove = node;
                    node.setId("lastClickedB");
                } else {
                    lastMove.setId("clickedW");
                    lastMove = node;
                    node.setId("lastClickedB");
                }
                break;
            }
        }
    }

    /**
     * Removes Events
     *
     * @author Jean
     */
    private void removeEvents() {
        isClosed = true;
        LOG.info("Removing click events");
        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            node.setOnMouseClicked(null);
        }
    }

    /**
     * OnClick event handler of grid
     * handles Move made by client, makes sure it is valid
     *
     * @param e mouseClick event
     * @throws IOException if the processing of packets fails
     * @author Jean Naima
     */
    private void clientMove(MouseEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Move move = new Move(GridPane.getRowIndex(node).byteValue(), GridPane.getColumnIndex(node).byteValue());
        if (board.checkIfValidMove(move) && numPieces != 0) {
            LOG.info(move.getX() + "      ::::      " + move.getY());
            board.play(move);
            movePlayedClient(move.getX(), move.getY());
            node.setOnMouseClicked(null);
            setPiecesRemaining();
            if (numPieces == 0) {
                connection.sendData(PacketInfo.QUIT, PacketInfo.PLAYER_ONE, (byte) move.getX(), (byte) move.getY());
                processReceivedData();
            } else {
                connection.sendData(PacketInfo.MOVE, PacketInfo.PLAYER_ONE, (byte) move.getX(), (byte) move.getY());
                processReceivedData();
            }
        }
    }

    /**
     * exit button event handler
     * closes socket and app
     *
     * @author Jean Naima
     */
    @FXML
    private void closeWindow() throws IOException {
        connection.closeSocket();
        Stage stage = (Stage) gridPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles pieces remaining for client Player
     *
     * @author Jean Naima
     */
    private void setPiecesRemaining() {
        numPieces--;
        piecesRemaining.setText("Pieces Remaining: " + numPieces);
    }
}
    
