package ca.qc.dawsoncollege.threestones.game;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.Network.PacketInfo;
import ca.qc.dawsoncollege.threestones.game.Network.ThreeStonesConnector;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class GameController {
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(GameController.class);
    Scanner input = new Scanner(System.in);
    int piecesRemaining;
    private ThreeStonesConnector connection;
    private Board board;


    public GameController() throws IOException {
        System.out.println("Please input port");
        int port = input.nextInt();
        InetAddress ip = InetAddress.getLocalHost();
        this.connection = new ThreeStonesConnector(new Socket(ip, port));
    }

    public void run() throws IOException {
        board = new Board();
        this.piecesRemaining = 15;
        do {
            Move m1 = board.computerMove();
            board.play(m1);
            this.piecesRemaining--;
            System.out.println("pieces remaining " + this.piecesRemaining);
            System.out.println(m1);
            System.out.println(board);
            if (piecesRemaining <= 0) {
                connection.sendData(PacketInfo.QUIT, PacketInfo.PLAYER_ONE, (byte) m1.getX(), (byte) m1.getY());
            } else {
                connection.sendData(PacketInfo.MOVE, PacketInfo.PLAYER_ONE, (byte) m1.getX(), (byte) m1.getY());
                processReceivedData();
            }
        } while (this.piecesRemaining > 0);
        System.out.println("out of pieces");
        System.out.println(board.calculateScore());
        connection.closeSocket();
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
                this.piecesRemaining = 0;
                break;
            default:
                LOG.info("No Data Received From Server");
        }
    }

    private void displayGame() {
        System.out.println(board.toString());
    }

}
