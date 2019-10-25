package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIPlayer extends Player {

    private static final double BLOCKED_WEIGHT = 0.6;
    private final ImmutableBoard board;

    public AIPlayer(TileState state, ImmutableBoard board) {
        if (state.isPlayable()) {
            throw new IllegalArgumentException("Invalid tile state");
        }

        this.board = board;
        this.numRemainingPieces = Board.NUM_PIECES;
    }

    public Move getMove() {
        Move m;
        Random rand = new Random();

        double bestScore = -1;
        do {
            List<Move> moves = new ArrayList<Move>();
            for (int x = 0; x < Board.WIDTH; x++) {
                for (int y = 0; y < Board.HEIGHT; y++) {
                    Board boardBlack = this.board.getBoardCopy();
                    Board boardWhite = this.board.getBoardCopy();

                    Move blackMove = new Move(x, y, TileState.BLACK);
                    if (this.board.checkIfValidMove(blackMove)) {
                        continue;
                    }

                    Move whiteMove = new Move(x, y, TileState.WHITE);

                    boardBlack.play(blackMove);
                    boardWhite.play(whiteMove);

                    Score currentScore = this.board.calculateScore();
                    Score blackScore = boardBlack.calculateScore();
                    Score whiteScore = boardWhite.calculateScore();

                    int blackIncrease = blackScore.getScore(TileState.BLACK) - currentScore.getScore(TileState.BLACK);
                    int whiteBlocked = whiteScore.getScore(TileState.WHITE) - currentScore.getScore(TileState.WHITE);

                    double weightedScore = blackIncrease + whiteBlocked * BLOCKED_WEIGHT;

                    if (weightedScore > bestScore) {
                        moves.clear();
                        moves.add(blackMove);
                        bestScore = weightedScore;
                    } else if (weightedScore == bestScore) {
                        moves.add(blackMove);
                    }
                }
            }

            m = moves.get(rand.nextInt(moves.size()));
        } while (this.board.checkIfValidMove(m));

        return m;
    }

}
