package ca.qc.dawsoncollege.threestones.game.GamePieces;

import ca.qc.dawsoncollege.threestones.game.Player.Player;

public class Game {
    public void run(Player... players) {
        Board board = new Board();
        System.out.println(board);

        boolean piecesRemaining = true;
        while (piecesRemaining) {
            piecesRemaining = false;

            for (Player player : players) {
                if (player.hasRemainingPieces()) {
                    Move m1 = player.getMove();
                    while (!board.checkIfValidMove(m1)) {
                        m1 = player.getMove();
                    }
                    board.play(m1);
                    player.usePiece();

                    if (player.hasRemainingPieces()) {
                        piecesRemaining = true;
                    }
                    System.out.println(board);
                }
            }

            System.out.println(board.calculateScore());
        }
    }
}
