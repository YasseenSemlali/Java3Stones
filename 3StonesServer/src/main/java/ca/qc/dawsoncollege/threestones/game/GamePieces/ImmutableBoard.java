/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.dawsoncollege.threestones.game.GamePieces;

/**
 *
 * @author Yasseen
 */
public class ImmutableBoard {
    private final Board board;
    
    public ImmutableBoard(Board board) {
        this.board = board;
    }
    
    public Tile get(int x, int y) {
        return this.board.get(x,y);
    }
    
    public boolean checkIfValidMove(Move move) {
        return this.board.checkIfValidMove(move);
    }
    
    public Score calculateScore() {
        return this.board.calculateScore();
    }
    
    public int getNumAdjacent(int x, int y, TileState state) {
        int n = 0;
        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                if(i != 0 || j != 0) {
                    try{
                        if(this.get(x + i, y + j).getState() == state) {
                            n++;
                        }
                    } catch(IndexOutOfBoundsException ignored) {
                        //ignore
                    }
                }
            }
        }
        return n;
    }
    
    public Board getBoardCopy() {
        return this.board.clone();
    }
    
    public int getWidth(){
        return Board.WIDTH;
    }
    
    public int getHeight(){
        return Board.HEIGHT;
    }
    
    public String toString() {
        return this.board.toString();
    }
}
