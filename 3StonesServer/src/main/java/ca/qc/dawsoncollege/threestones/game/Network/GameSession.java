package ca.qc.dawsoncollege.threestones.game.Network;


import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Score;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * class that handles the game logic
 *
 * @author Saad
 */
public class GameSession {
    private final static Logger LOG = LoggerFactory.getLogger(GameSession.class);
    private ThreeStonesConnector connection;
    private Board board;
    private int piecesRemaining;

    /**
     * This will run one game within the code.
     *
     * @param player1 representing the player who made the move.
     * @author Saad
     * @author Yasseen
     */
    public GameSession(Socket player1) {
        this.connection = new ThreeStonesConnector(player1);
        LOG.info("Game session created");
        board = new Board();
    }

    public void run() {
        try {
            this.piecesRemaining = 15;
            do {
                byte[] data = connection.receiveData();
                System.out.println(data[0]);
                if (data[0] == PacketInfo.QUIT) {
                    LOG.info("Quitting game...");
                    board.addMove(data[2], data[3], PacketInfo.PLAYER_ONE);
                    System.out.println(board);
                    this.piecesRemaining = 0;
                } else {
                    //LOG.info("Adding move at line " + data[2] + "," + data[3] + " for player.");
                    board.addMove(data[2], data[3], PacketInfo.PLAYER_ONE);
                    System.out.println(board);
                    serverMove();
                }
            } while (this.piecesRemaining > 0);
            Score current = board.calculateScore();
            checkWinner(current);
            connection.closeSocket();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        System.exit(0);
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

    /**
     * This method will send the client the results of his movement.
     *
     * @author Saad
     * @author Yasseen
     */
    private void serverMove() throws IOException {
        byte first;
        byte second;
        byte third;
        byte fourth;

        Move decision = board.computerMove();
        board.addMove((byte) decision.getX(), (byte) decision.getY(), PacketInfo.PLAYER_TWO);
        this.piecesRemaining--;
        System.out.println("pieces remaining " + this.piecesRemaining);
        System.out.println(board);

        if (this.piecesRemaining <= 0) {
            LOG.info("No more pieces.");
            first = PacketInfo.QUIT;
            second = PacketInfo.PLAYER_TWO;
            third = (byte) decision.getX();
            fourth = (byte) decision.getY();
        } else {
            first = PacketInfo.MOVE;
            second = PacketInfo.PLAYER_TWO;
            third = (byte) decision.getX();
            fourth = (byte) decision.getY();
        }
        //LOG.info("Computer is returning his move to client at line: " + decision);
        connection.sendData(first, second, third, fourth);

    }
}


