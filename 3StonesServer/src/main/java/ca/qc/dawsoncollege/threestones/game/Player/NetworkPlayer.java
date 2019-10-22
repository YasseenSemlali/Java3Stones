package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;
import ca.qc.dawsoncollege.threestones.game.Network.ThreeStonesConnector;
import java.io.IOException;

public class NetworkPlayer extends Player {

    private final TileState tileColor;
    private ThreeStonesConnector connection;

    public NetworkPlayer(TileState state, ThreeStonesConnector connection) {
        if (!state.isPlayable()) {
            throw new IllegalArgumentException("Invalid tile state");
        }

        this.numRemainingPieces = Board.NUM_PIECES;
        this.tileColor = state;
        this.connection = connection;
    }

    public Move getMove() throws IOException {
        byte[] data = connection.getReceivedData();
        
        return new Move(data[2], data[3], this.tileColor);
    }

}
