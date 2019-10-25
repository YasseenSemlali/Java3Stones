package ca.qc.dawsoncollege.threestones.game.GamePieces;

/**
 * How tile enum is used and where
 *
 * @author Jean
 * @author Yasseen
 */
public enum TileState {
    EMPTY, BLACK, WHITE;

    /**
     * Gives the tile states available in the game
     *
     * @return available tiles
     */
    public static TileState[] getPlayablePieces() {
        return new TileState[]{WHITE, BLACK};
    }

    /**
     * returns tile char by type
     *
     * @return string character for tile
     */
    public String toString() {
        switch (this) {
            case BLACK:
                return " B ";
            case EMPTY:
                return " X ";
            case WHITE:
                return " W ";
            default:
                return " ";
        }
    }
}
