package ca.qc.dawsoncollege.threestones.game.GamePieces;

/**
 * Tile object
 *
 * @author Yasseen
 */
public class Tile implements Cloneable {
    private final int x;
    private final int y;
    private TileState state;

    /**
     * Tile Object
     *
     * @param x     x-coordinate of tile
     * @param y     y-coordinate of tile
     * @param state tiles colour for the game
     */
    public Tile(int x, int y, TileState state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    /**
     * Creates copy of tile object
     *
     * @return copy of tile
     */
    public Tile clone() {
        return new Tile(x, y, state);
    }

    /**
     * Set the tiles state
     *
     * @param state the colour we want the tile to be
     */
    public void setTileState(TileState state) {
        this.state = state;
    }

    /**
     * Get the tiles state
     *
     * @return Tile state representing tile colour
     */
    public TileState getState() {
        return this.state;
    }

    /**
     * Check if tile is empty
     *
     * @return tile being occupied or empty
     */
    public boolean isEmpty() {
        return this.state == TileState.EMPTY;
    }

    /**
     * tile state
     *
     * @return string state
     */
    public String toString() {
        return this.state.toString();
    }
}
