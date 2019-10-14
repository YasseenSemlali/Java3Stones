package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Board;
import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;
import ca.qc.dawsoncollege.threestones.game.GamePieces.TileState;

import java.util.Scanner;

public class NetworkPlayer extends Player {

    private final TileState tileColor;
    Scanner input = new Scanner(System.in);

    public NetworkPlayer(TileState state) {
        if (!state.isPlayable()) {
            throw new IllegalArgumentException("Invalid tile state");
        }

        this.numRemainingPieces = Board.NUM_PIECES;
        this.tileColor = state;
    }

    public Move getMove() {
        System.out.println("Player1 Move");
        System.out.println("x");
        int x = input.nextInt();
        System.out.println("y");
        int y = input.nextInt();
        return new Move(x, y, this.tileColor);
    }

}
