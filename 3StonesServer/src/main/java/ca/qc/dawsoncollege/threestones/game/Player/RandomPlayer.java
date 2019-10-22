package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;

public class RandomPlayer extends Player {

    private final TileState tileColor;

    public RandomPlayer(TileState state) {
        if (!state.isPlayable()) {
            throw new IllegalArgumentException("Invalid tile state");
        }

        this.numRemainingPieces = Board.NUM_PIECES;
        this.tileColor = state;
    }

    public Move getMove() {
        return new Move((int) (Math.random() * 11), (int) (Math.random() * 11), this.tileColor);
    }


}
