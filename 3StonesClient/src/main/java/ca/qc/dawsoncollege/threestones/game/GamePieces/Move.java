package ca.qc.dawsoncollege.threestones.game.GamePieces;

public class Move {
    private int x;
    private int y;
    private TileState state;

    public Move(int x, int y, TileState state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public Move(byte x, byte y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileState getState() {
        return state;
    }

    public void setState(TileState state) {
        this.state = state;
    }
}
