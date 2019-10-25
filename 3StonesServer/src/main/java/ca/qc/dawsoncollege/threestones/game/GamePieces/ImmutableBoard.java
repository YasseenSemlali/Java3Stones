/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.dawsoncollege.threestones.game.GamePieces;

/**
 * Board object that cannot be changed
 *
 * @author Yasseen
 */
public class ImmutableBoard {
    private final Board board;

    /**
     * create immutable board based off another board
     *
     * @param board original board
     */
    public ImmutableBoard(Board board) {
        this.board = board;
    }

    /**
     * check if move played is valid on board
     *
     * @param move move played
     * @return depending if move is valid
     */
    public boolean checkIfValidMove(Move move) {
        return this.board.checkIfValidMove(move);
    }

    /**
     * check score for players on board
     *
     * @return Score for players
     */
    public Score calculateScore() {
        return this.board.calculateScore();
    }

    /**
     * get tile
     *
     * @param x x coordinate of tile
     * @param y y coordinate of tile
     * @return Tile selected
     */
    private Tile get(int x, int y) {
        return this.board.get(x, y);
    }

    /**
     * get the number of adjacent
     *
     * @param x     x coordinate
     * @param y     y coordinate
     * @param state tile state of move
     * @return
     */
    public int getNumAdjacent(int x, int y, TileState state) {
        int n = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    try {
                        if (this.get(x + i, y + j).getState() == state) {
                            n++;
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                        //ignore
                    }
                }
            }
        }
        return n;
    }

    /**
     * @return copy of immutable board
     */
    public Board getBoardCopy() {
        return this.board.clone();
    }

    /**
     * @return string representation of board
     */
    public String toString() {
        return this.board.toString();
    }
}
