package ca.qc.dawsoncollege.threestones.game;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;
import ca.qc.dawsoncollege.threestones.game.Network.PacketInfo;
import ca.qc.dawsoncollege.threestones.game.Network.ThreeStonesConnector;
import ca.qc.dawsoncollege.threestones.game.Player.Player;
import ca.qc.dawsoncollege.threestones.game.Player.RandomPlayer;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class GameController {
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(GameController.class);
    Player p1 = new RandomPlayer(TileState.WHITE);
    Scanner input = new Scanner(System.in);
    private ThreeStonesConnector connection;
    private Board board;

    public GameController() throws IOException {
        System.out.println("Please input port");
        int port = input.nextInt();
        InetAddress ip = InetAddress.getLocalHost();
        this.connection = new ThreeStonesConnector(new Socket(ip, port));
    }

    public void run() throws IOException {
        boolean play = true;
        board = new Board();
        do {
            Move m1 = p1.getMove();
            System.out.println(m1.getState());
            System.out.println(m1.getX());
            System.out.println(m1.getY());
            connection.sendData(PacketInfo.MOVE, PacketInfo.PLAYER_ONE, (byte) m1.getX(), (byte) m1.getY());
            processReceivedData();
            if (board.checkIfWin()) {
                play = false;
            }
        } while (play);
    }

    public void processReceivedData() throws IOException {
        byte[] data = connection.receiveData();
        //data[0]: move message type
        //data[1]: player's turn
        //data[2]: move location
        //data[3]:'move location
        switch (data[0]) {
            case PacketInfo.MOVE:
                System.out.println("My move");
                board.addMove(data[2], data[3], data[1]);
                displayGame();
                break;
            case PacketInfo.PLAY:
                board = new Board();
                displayGame();
                break;
            case PacketInfo.QUIT:
                connection.closeSocket();
                break;
            case PacketInfo.WIN:
                board.addMove(data[2], data[3], data[1]);
                displayGame();
                System.out.println("win");
                connection.closeSocket();
                break;
            case PacketInfo.TIE:
                board.addMove(data[2], data[3], data[1]);
                displayGame();
                System.out.println("tie");
                break;
            default:
                LOG.info("No Data Received From Server");
        }
    }

    private void displayGame() {
        System.out.println(board.toString());
    }

}
