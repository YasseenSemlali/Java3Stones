package ca.qc.dawsoncollege.threestones.game.Network;

import ca.qc.dawsoncollege.threestones.game.GamePieces.*;
import ca.qc.dawsoncollege.threestones.game.Player.AIPlayer;
import ca.qc.dawsoncollege.threestones.game.Player.NetworkPlayer;
import ca.qc.dawsoncollege.threestones.game.Player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * class that handles the game logic
 *
 * @author Saad
 * @author Yasseen
 */
public class GameSession implements Runnable {

    private final static Logger LOG = LoggerFactory.getLogger(GameSession.class);
    private ThreeStonesConnector connection;
    private Board board;
    private Player p1;
    private Player p2;

    /**
     * This will start one game session
     *
     * @param player1 representing the player who made the move.
     * @author Saad
     * @author Yasseen
     */
    public GameSession(Socket player1) {
        this.connection = new ThreeStonesConnector(player1);
        LOG.info("Connected with user from : " + player1.getInetAddress().toString());
    }

    /**
     * Creates an instance of a game which can be used by multiple clients
     * 
     * @author Saad
     */
    @Override
    public void run() {
        boolean notClosed = true;
        LOG.info("Game session created");
        board = new Board();

        p1 = new NetworkPlayer(TileState.WHITE, connection);
        p2 = new AIPlayer(TileState.BLACK, new ImmutableBoard(board));
        try {
            do {
                connection.receiveData();
                byte[] data = connection.getReceivedData();

                if (data[0] == PacketInfo.QUIT) {
                    LOG.info("Quitting game...");

                    this.playTurn(data);

                    p2.setNumRemainingPieces(0);
                } else if (data[0] == 0) {
                    LOG.info("closeSocket");
                    connection.closeSocket();
                    notClosed = false;
                    break;
                } else if (data[0] == PacketInfo.NEW_GAME) {
                    run();
                } else {
                    this.playTurn(data);

                }
            } while (p2.hasRemainingPieces());

            Score current = board.calculateScore();
            checkWinner(current);

            while (notClosed) {
                connection.receiveData();
                byte[] data = connection.getReceivedData();
                if (data[0] == PacketInfo.NEW_GAME) {
                    run();
                } else if (data[0] == 0) {
                    notClosed = false;
                    LOG.info("Socket Closed");
                    connection.closeSocket();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * play computer move on board
     *
     * @param data move computer is playing
     * @throws IOException
     */
    private void playTurn(byte[] data) throws IOException {
        LOG.info("Adding move at line " + data[2] + "," + data[3] + " for player.");

        Move p1Move = p1.getMove();
        board.play(p1Move);
        p1.usePiece();

        Move p2Move = p2.getMove();
        board.play(p2Move);
        p2.usePiece();

        this.sendClientMoveInfo(p2Move);
    }

    /**
     * check winner based on score
     *
     * @param current current boards score
     */
    private void checkWinner(Score current) {
        if (current.getScore(TileState.WHITE) == current.getScore(TileState.BLACK)) {
            LOG.info("Tie Game");
        } else if (current.getScore(TileState.WHITE) > current.getScore(TileState.BLACK)) {
            LOG.info("Winner White");
        } else {
            LOG.info("Winner Black");
        }
    }

    /**
     * This method will send the client the results of a Move.
     *
     * @author Saad
     * @author Yasseen
     */
    private void sendClientMoveInfo(Move move) throws IOException {
        byte first;
        byte second;
        byte third;
        byte fourth;
        byte fifth;
        byte sixth;
        Score current = board.calculateScore();

        if (!p2.hasRemainingPieces()) {
            LOG.info("No more pieces.");
            first = PacketInfo.QUIT;
            second = PacketInfo.PLAYER_TWO;
            third = (byte) move.getX();
            fourth = (byte) move.getY();
            fifth = (byte) current.getScore(TileState.WHITE);
            sixth = (byte) current.getScore(TileState.BLACK);

        } else {
            first = PacketInfo.MOVE;
            second = PacketInfo.PLAYER_TWO;
            third = (byte) move.getX();
            fourth = (byte) move.getY();
            fifth = (byte) current.getScore(TileState.WHITE);
            sixth = (byte) current.getScore(TileState.BLACK);
        }
        //LOG.info("Computer is returning his move to client at line: " + decision);
        connection.sendData(first, second, third, fourth, fifth, sixth);
    }
}
