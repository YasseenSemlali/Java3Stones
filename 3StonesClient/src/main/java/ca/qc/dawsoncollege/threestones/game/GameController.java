package ca.qc.dawsoncollege.threestones.game;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Score;
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
    Scanner input = new Scanner(System.in);
    private Player p1;
    private ThreeStonesConnector connection;
    private Board board;


    public GameController() throws IOException {
        int port = 50000;
        InetAddress ip = InetAddress.getLocalHost();
        this.connection = new ThreeStonesConnector(new Socket(ip, port));
    }

    public void run() throws IOException {
        board = new Board();
        p1 = new RandomPlayer(TileState.WHITE);
        do {
            Move m1 = computerMove();
            board.play(m1);
            p1.usePiece();
            System.out.println(m1);
            System.out.println("pieces remaining " + p1.getNumRemainingPieces());
            System.out.println(board);
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
                displayGame();
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
        return move;
    }

    private void displayGame() {
        System.out.println(board);
    }

}
