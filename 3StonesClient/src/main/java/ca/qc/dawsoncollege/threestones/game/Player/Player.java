package ca.qc.dawsoncollege.threestones.game.Player;

import ca.qc.dawsoncollege.threestones.game.GamePieces.Move;

public abstract class Player {

    protected int numRemainingPieces;

    public abstract Move getMove();

}
