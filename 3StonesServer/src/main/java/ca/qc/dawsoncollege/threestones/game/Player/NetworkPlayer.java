package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;
import ca.qc.dawsoncollege.threestones.game.Network.ThreeStonesConnector;

import java.io.IOException;

/**
 * class representing player
 */
public class NetworkPlayer extends Player {

    private final TileState tileColor;
    private ThreeStonesConnector connection;

    /**
     * instantiate player
     *
     * @param state      colour player will be
     * @param connection connection to send packets
     */
    public NetworkPlayer(TileState state, ThreeStonesConnector connection) {
        if (state.isPlayable()) {
            throw new IllegalArgumentException("Invalid tile state");
        }

        this.numRemainingPieces = Board.NUM_PIECES;
        this.tileColor = state;
        this.connection = connection;
    }

    /**
     * get move from server and play on board
     *
     * @return Move from server
     * @throws IOException in case move is not retained
     */
    public Move getMove() throws IOException {
        byte[] data = connection.getReceivedData();
        return new Move(data[2], data[3], this.tileColor);
    }

}
