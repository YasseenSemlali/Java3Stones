package ca.qc.dawsoncollege.threestones.game.GamePieces;

public class Tile {
    private final int x;
    private final int y;
    private TileState state;

    public Tile(int x, int y, TileState state) {
        this.x = x;
        this.y = y;
        this.state = state;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return this.state.toString();
    }
}
