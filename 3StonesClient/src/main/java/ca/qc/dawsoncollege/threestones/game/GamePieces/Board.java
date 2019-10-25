package ca.qc.dawsoncollege.threestones.game.GamePieces;

import ca.qc.dawsoncollege.threestones.game.Network.PacketInfo;

/**
 * Board Object
 *
 * @author Saad
 * @author Yasseen
 * @author Jean
 */
public class Board implements Cloneable {
    public static final int HEIGHT = 11;
    public static final int WIDTH = 11;

    private static final int STONES_FOR_POINT = 3;
    private int lastPlayedX = -1;
    private int lastPlayedY = -1;
    private Tile[][] grid;

    /**
     * create new board object
     */
    public Board() {
        this.grid = new Tile[WIDTH][HEIGHT];
        this.populateGrid();
    }

    /**
     * create copy of board
     *
     * @param grid        tiles and positions of pieces
     * @param lastPlayedX last played x
     * @param lastPlayedY last played y
     */
    private Board(Tile[][] grid, int lastPlayedX, int lastPlayedY) {
        this.grid = grid;

        this.lastPlayedX = lastPlayedX;
        this.lastPlayedY = lastPlayedY;
    }

    /**
     * populate grid with empty values
     */
    private void populateGrid() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                this.grid[x][y] = new Tile(x, y, TileState.EMPTY);
            }
        }
    }

    /**
     * get tile on grid
     *
     * @param x x coordinate on grid
     * @param y y coordinate on grid
     * @return Tile atx y position
     */
    public Tile get(int x, int y) {
        return this.grid[x][y];
    }

    /**
     * play a move on the board
     *
     * @param move the move that either player or computer has made
     */
    public void play(Move move) {
        int x = move.getX();
        int y = move.getY();
        if (!checkIfValidMove(move))
            throw new IllegalArgumentException("Invalid move");

        if (move.getState() == TileState.EMPTY)
            throw new IllegalArgumentException("Invalid state");

        this.get(x, y).setTileState(move.getState());
        this.lastPlayedX = x;
        this.lastPlayedY = y;
    }

    /**
     * check if move that was made is even valid
     *
     * @param move move by AI or Player
     * @return
     */
    public boolean checkIfValidMove(Move move) {
        int x = move.getX();
        int y = move.getY();
        return this.get(x, y).isEmpty() && x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && this.validateLastPlayed(x, y);
    }

    /**
     * validate move that was last played
     *
     * @param x previous x coordinate
     * @param y previous y coordinate
     * @return whether or not last move was valid
     */
    private boolean validateLastPlayed(int x, int y) {
        if (this.lastPlayedX == -1 && this.lastPlayedY == -1)
            return true;

        if (colFree(x) || rowFree(y)) {
            return x == this.lastPlayedX || y == this.lastPlayedY;
        }
        return true;
    }

    /**
     * check if col is free
     *
     * @param x which col
     * @return whether it is free
     */
    private boolean colFree(int x) {
        for (int y = 0; y < WIDTH; y++) {
            if (this.get(x, y).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if row is free
     *
     * @param y which row
     * @return whether it is free
     */
    private boolean rowFree(int y) {
        for (int x = 0; x < HEIGHT; x++) {
            if (this.get(x, y).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * build string if board
     *
     * @return string representation of board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int j = HEIGHT - 1; j >= 0; j--) {
            for (int i = 0; i < WIDTH; i++) {
                sb.append(this.get(i, j));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * create move at location
     *
     * @param x      x coordinate of move
     * @param y      y coordinate of move
     * @param player the player that played the move
     */
    public void addMove(byte x, byte y, byte player) {
        Move move = new Move(x, y);
        if (player == PacketInfo.PLAYER_ONE) {
            move.setState(TileState.WHITE);
        } else if (player == PacketInfo.PLAYER_TWO) {
            move.setState(TileState.BLACK);
        }
        play(move);
    }

    /**
     * get a clone of the grid object
     *
     * @return tile array representing the grid
     */
    private Tile[][] getGridClone() {
        Tile[][] grid = new Tile[WIDTH][HEIGHT];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = this.grid[i][j].clone();
            }
        }

        return grid;
    }

    /**
     * create clone of grid
     *
     * @return new board freshly cloned
     */
    public Board clone() {
        return new Board(getGridClone(), lastPlayedX, lastPlayedY);
    }
}
