package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.ImmutableBoard;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Score;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AIPlayer extends Player {

   private final TileState tileColor;
   private final ImmutableBoard board;
   
   private static final double BLOCKED_WEIGHT = 0.6;

    public AIPlayer(TileState state, ImmutableBoard board) {
        if (!state.isPlayable()) {
            throw new IllegalArgumentException("Invalid tile state");
        }

        this.board = board;
        
        
        this.numRemainingPieces = Board.NUM_PIECES;
        this.tileColor = state;
    }

    public Move getMove() {
        Move m;
        Random rand = new Random();
        
        double bestScore = -1;
        do{
            List<Move> moves = new ArrayList<Move>();
            for(int x = 0; x < Board.WIDTH; x++) {
                for(int y = 0; y < Board.HEIGHT; y++) {
                    Board boardBlack = this.board.getBoardCopy();
                    Board boardWhite = this.board.getBoardCopy();
                    
                    Move blackMove = new Move(x,y,TileState.BLACK);                    
                    if(!this.board.checkIfValidMove(blackMove)) {
                        continue;
                    }
                    
                    Move whiteMove = new Move(x,y,TileState.WHITE);
                    
                    boardBlack.play(blackMove);
                    boardWhite.play(whiteMove);
                    
                    Score currentScore = this.board.calculateScore();
                    Score blackScore = boardBlack.calculateScore();
                    Score whiteScore = boardWhite.calculateScore();
                    
                    int blackIncrease = blackScore.getScore(TileState.BLACK) - currentScore.getScore(TileState.BLACK);
                    int whiteBlocked =  whiteScore.getScore(TileState.WHITE) - currentScore.getScore(TileState.WHITE);
                    
                    double weightedScore = blackIncrease + whiteBlocked * BLOCKED_WEIGHT;
                    
                    //System.out.println(this.board);
                    //System.out.println(boardBlack);
                    //System.out.println("Current: " + this.board.calculateScore());
                    //System.out.println("Black: " + boardBlack.calculateScore());
                    //System.out.println("White: " + whiteScore);
                    //System.out.println("Black increase: " + blackIncrease);
                    //System.out.println("White increase: " + whiteBlocked);
                    
                    if(weightedScore > bestScore) {
                        moves.clear();
                        moves.add(blackMove);
                        bestScore = weightedScore;
                    } else if (weightedScore == bestScore) {
                        moves.add(blackMove);
                    }
                }
            }
            
            m = moves.get(rand.nextInt(moves.size()));
            //m = new Move((int) (Math.random() * 11), (int) (Math.random() * 11), this.tileColor);
        } while(!this.board.checkIfValidMove(m));
        
        return m;
    }

}
