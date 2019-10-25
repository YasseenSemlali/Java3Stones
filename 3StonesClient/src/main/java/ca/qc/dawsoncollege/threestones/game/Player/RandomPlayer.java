package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.ImmutableBoard;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;

public class RandomPlayer extends Player {

    private final TileState tileColor;
    private final ImmutableBoard board;

    public RandomPlayer(TileState state, ImmutableBoard board) {
        if (state.isPlayable()) {
            throw new IllegalArgumentException("Invalid tile state");
        }

        this.board = board;
        this.numRemainingPieces = Board.NUM_PIECES;
        this.tileColor = state;
    }

    public Move getMove() {
        Move m;

        do {
            m = new Move((int) (Math.random() * 11), (int) (Math.random() * 11), this.tileColor);
        } while (this.board.checkIfValidMove(m));
        return m;
    }
}
