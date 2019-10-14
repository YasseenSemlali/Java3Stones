package ca.qc.dawsoncollege.threestones.game.Network;


import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

/**
 * class that handles the game logic
 *
 * @author Saad
 */
public class GameSession{
    private final static Logger LOG = LoggerFactory.getLogger(GameSession.class);
    private ThreeStonesConnector connection;
    private Board board;
    private boolean piecesRemaining;

    /**
     * This will start one game session
     *
     * @param player1 representing the player who made the move.
     * @author Saad
     * @author Yasseen
     */
    public GameSession(Socket player1) {
        this.connection = new ThreeStonesConnector(player1);
        LOG.info("Game session created");
        board = new Board();
        piecesRemaining = true;
        try {
            do {
                byte[] data = connection.receiveData();
                if (data[0] == PacketInfo.QUIT) {
                    LOG.info("Quitting game...");
                    piecesRemaining = false;
                } else if (data[0] == PacketInfo.PLAY) {
                    LOG.info("Starting game...");
                    board = new Board();
                } else if (data[0] == PacketInfo.WIN) {
                    LOG.info("Restarting game...");
                    board = new Board();
                } else {
                    LOG.info("Adding move at line " + data[2] + "," + data[3] + " for player.");
                    serverMove(data[2], data[3]);
                }
            } while (piecesRemaining);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * This method will send the client the results of his movement.
     *
     * @param x representing the x that player has chosen
     * @param y representing the y that player has chosen
     * @author Saad
     * @author Yasseen
     */
    private void serverMove(byte x, byte y) throws IOException {
        System.out.println("here");
        byte first;
        byte second;
        byte third;
        byte fourth;
        board.addMove(x, y, PacketInfo.PLAYER_ONE);
        System.out.println(board.toString());
        if (board.checkIfWin()) {
            LOG.info("Player is making a victory move.");
            first = PacketInfo.WIN;
            second = PacketInfo.PLAYER_ONE;
            third = PacketInfo.SPACE;
            fourth = PacketInfo.SPACE;
        } else if (board.checkIfTie()) {
            LOG.info("Player has made the game a tie.");
            first = PacketInfo.TIE;
            second = PacketInfo.PLAYER_ONE;
            third = PacketInfo.SPACE;
            fourth = PacketInfo.SPACE;
        } else {
            Move decision = board.computerMove();
            board.addMove((byte) decision.getX(), (byte) decision.getY(), PacketInfo.PLAYER_TWO);
            LOG.info("Adding move at line " + decision + " for computer.");
            if (board.checkIfWin()) {
                LOG.info("Computer is making a victory move.");
                first = PacketInfo.WIN;
                second = PacketInfo.PLAYER_TWO;
                third = (byte) decision.getX();
                fourth = (byte) decision.getY();
            } else if (board.checkIfTie()) {
                LOG.info("Computer has made the game a tie.");
                first = PacketInfo.TIE;
                second = PacketInfo.PLAYER_TWO;
                third = (byte) decision.getX();
                fourth = (byte) decision.getY();
            } else {
                LOG.info("Computer has not made " + "a victory or tie move.");
                first = PacketInfo.MOVE;
                second = PacketInfo.PLAYER_TWO;
                third = (byte) decision.getX();
                fourth = (byte) decision.getY();
            }
            LOG.info("Computer is returning his move to client at line: " + decision);
        }
        connection.sendData(first, second, third, fourth);
    }
}


