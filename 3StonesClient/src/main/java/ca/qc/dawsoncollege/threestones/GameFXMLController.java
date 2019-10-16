/**
 * Sample Skeleton for 'gameFXML.fxml' Controller Class
 */

package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Score;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;
import ca.qc.dawsoncollege.threestones.game.Network.PacketInfo;
import ca.qc.dawsoncollege.threestones.game.Network.ThreeStonesConnector;
import ca.qc.dawsoncollege.threestones.game.Player.Player;
import ca.qc.dawsoncollege.threestones.game.Player.RandomPlayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameFXMLController {

    private final static Logger LOG =  LoggerFactory.getLogger(GameFXMLController.class);
    
    Board board;
    
    Player p1;
    
    ThreeStonesConnector connection;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="gridPane"
    private GridPane gridPane; // Value injected by FXMLLoader
        
    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() throws IOException {
        // TODO
        addEventGrid();
        //startGame();
    }      
    private void addEventGrid() {
        ObservableList <Node> children = gridPane.getChildren();
        LOG.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        for(Node node : children){
            node.setId("circle");
        }
        
    }
    public void setConnection(String ip, int port) throws IOException{
        connection = new ThreeStonesConnector(ip,port);
    }
    public void startGame() throws IOException {
        board = new Board();
        p1 = new RandomPlayer(TileState.WHITE);
        do {
            Move m1 = computerMove();
            board.play(m1);
            p1.usePiece();
          //  System.out.println(m1);
           // System.out.println("pieces remaining " + p1.getNumRemainingPieces());
           // System.out.println(board);
            if (!p1.hasRemainingPieces()) {
                connection.sendData(PacketInfo.QUIT, PacketInfo.PLAYER_ONE, (byte) m1.getX(), (byte) m1.getY());
            } else {
                connection.sendData(PacketInfo.MOVE, PacketInfo.PLAYER_ONE, (byte) m1.getX(), (byte) m1.getY());
                processReceivedData();
            }
        } while (p1.hasRemainingPieces());
        Score current = board.calculateScore();
        checkWinner(current);
        connection.closeSocket();
    }

    private void checkWinner(Score current) {
        System.out.println(current);
        if (current.getScore(TileState.WHITE) == current.getScore(TileState.BLACK)) {
            System.out.println("Tie Game");
        } else if (current.getScore(TileState.WHITE) > current.getScore(TileState.BLACK)) {
            System.out.println("Winner White");
        } else {
            System.out.println("Winner Black");
        }
    }

    public void processReceivedData() throws IOException {
        byte[] data = connection.receiveData();
        //data[0]: move message type
        //data[1]: player's turn
        //data[2]: move location
        //data[3]:'move location
        switch (data[0]) {
            case PacketInfo.MOVE:
                board.addMove(data[2], data[3], data[1]);
                break;
            case PacketInfo.QUIT:
                System.out.println("quiting");
                board.addMove(data[2], data[3], data[1]);
                p1.setNumRemainingPieces(0);
                break;
            default:
                LOG.info("No Data Received From Server");
        }
    }

    public Move computerMove() {
        Move move;
        do {
            move = p1.getMove();
        } while (!board.checkIfValidMove(move));
        movePlayedComputer(move.getX(),move.getY());
        return move;
    }
    public void movePlayedComputer(int x, int y){
        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == x && gridPane.getColumnIndex(node) == y) {
            node.setId("clickedW");
        }
    }
    }
}
    
