package ca.qc.dawsoncollege.threestones.game.GamePieces;

public class Tile implements Cloneable {
    private final int x;
    private final int y;
    private TileState state;

    public Tile(int x, int y, TileState state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public Tile clone() {
        return new Tile(x, y, state);
    }

    public void setTileState(TileState state) {
        this.state = state;
    }

    public TileState getState() {
        return this.state;
    }

    public boolean isEmpty() {
        return this.state == TileState.EMPTY;
    }

    public String toString() {
        return this.state.toString();
    }
}
