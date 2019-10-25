package ca.qc.dawsoncollege.threestones.game.GamePieces;

/**
 * Move object
 *
 * @author Yasseem
 */
public class Move {
    private int x;
    private int y;
    private TileState state;

    /**
     * create Move object
     *
     * @param x     x coordinate for move
     * @param y     y coordinate for move
     * @param state state of move
     */
    public Move(int x, int y, TileState state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    /**
     * String representation of move
     *
     * @return x and y coordinate of move
     */
    @Override
    public String toString() {
        return "Move{" +
                "x=" + x +
                ", y=" + y +
                ", state=" + state +
                '}';
    }

    /**
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @return state of the move
     */
    public TileState getState() {
        return state;
    }

    /**
     * change the state of the move
     *
     * @param state state that you want the move to be
     */
    public void setState(TileState state) {
        this.state = state;
    }
}

