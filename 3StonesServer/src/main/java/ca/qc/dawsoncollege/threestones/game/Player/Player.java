package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import java.io.IOException;

public abstract class Player {
    protected int numRemainingPieces;

    public abstract Move getMove() throws IOException;


    public int getNumRemainingPieces() {
        return this.numRemainingPieces;
    }

    public void setNumRemainingPieces(int pieces) {
        this.numRemainingPieces = pieces;
    }

    public boolean hasRemainingPieces() {
        return this.numRemainingPieces > 0;
    }

    public void usePiece() {
        if (this.numRemainingPieces <= 0) {
            throw new IllegalStateException("No pieces remaining");
        }
        this.numRemainingPieces--;
    }

}
