
package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;

import java.io.IOException;

/**
 * Abstract layer for player classes
 *
 * @author Yasseen
 */
public abstract class Player {
    protected int numRemainingPieces;

    public abstract Move getMove() throws IOException;

    /**
     * set number of pieces remaining for player
     *
     * @param pieces number to set
     */
    public void setNumRemainingPieces(int pieces) {
        this.numRemainingPieces = pieces;
    }

    /**
     * check if pieces remain
     *
     * @return check
     */
    public boolean hasRemainingPieces() {
        return this.numRemainingPieces > 0;
    }

    /**
     * check if pieces are empty if not use one
     */
    public void usePiece() {
        if (this.numRemainingPieces <= 0) {
            throw new IllegalStateException("No pieces remaining");
        }
        this.numRemainingPieces--;
    }

}
