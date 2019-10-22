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
