package ca.qc.dawsoncollege.threestones.game.Network;


import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.ImmutableBoard;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Score;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;
import ca.qc.dawsoncollege.threestones.game.Player.AIPlayer;
import ca.qc.dawsoncollege.threestones.game.Player.Player;
import ca.qc.dawsoncollege.threestones.game.Player.RandomPlayer;
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
    }

    public void run() throws IOException {
        boolean notClosed = true;
        LOG.info("Game session created");
        board = new Board();  
        p2 = new AIPlayer(TileState.BLACK, new ImmutableBoard(board));
        try {
            do {
                byte[] data = connection.receiveData();
                if (data[0] == PacketInfo.QUIT) {
                    LOG.info("Quitting game...");
                    board.addMove(data[2], data[3], PacketInfo.PLAYER_ONE);
                    System.out.println(board);
                    serverMove();
                    p2.setNumRemainingPieces(0);
                }else if (data[0] == 0 ){
                    LOG.info("closeSocket");
                    connection.closeSocket();
                    notClosed = false;
                    break;
                }else if(data[0] == PacketInfo.NEW_GAME){
                    run();
                }
                else{
                    LOG.info("Adding move at line " + data[2] + "," + data[3] + " for player.");
                    board.addMove(data[2], data[3], PacketInfo.PLAYER_ONE);
                    System.out.println(board);
                    serverMove();
                }
            } while (p2.hasRemainingPieces());
            Score current = board.calculateScore();
            checkWinner(current);
           do{
               byte[] data = connection.receiveData();
               if(data[0] == PacketInfo.NEW_GAME){
                   run();
               }
               else{
                   notClosed = false;
                   LOG.info("Socket Closed");
                   connection.closeSocket();
               }
           } 
           while(notClosed);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
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

        Move decision = computerMove();
        board.addMove((byte) decision.getX(), (byte) decision.getY(), PacketInfo.PLAYER_TWO);
        p2.usePiece();
        System.out.println("pieces remaining " + p2.getNumRemainingPieces());
        System.out.println(board);

        if (!p2.hasRemainingPieces()) {
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

    public Move computerMove() {
        Move move = null;
        do {
            move = p2.getMove();
        } while (!board.checkIfValidMove(move));
        return move;
    }
}


