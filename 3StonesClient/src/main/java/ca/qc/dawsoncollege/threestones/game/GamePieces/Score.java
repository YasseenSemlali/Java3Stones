package ca.qc.dawsoncollege.threestones.game.GamePieces;

import java.util.HashMap;

public class Score {
    private HashMap<TileState, Integer> scoreMap = new HashMap<TileState, Integer>();

    public void setScore(TileState state, int points) {
        scoreMap.put(state, points);
    }

    public String toString() {
        String s = "";

        for (TileState state : scoreMap.keySet()) {
            s += state + ": " + scoreMap.get(state) + " | ";
        }

        return s;
    }

}
