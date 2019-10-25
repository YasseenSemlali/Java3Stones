package ca.qc.dawsoncollege.threestones.game.GamePieces;

import java.util.HashMap;

/**
 * Tracks scores using points and states
 *
 * @author Yasseen
 */
public class Score {
    private HashMap<TileState, Integer> scoreMap = new HashMap<TileState, Integer>();

    /**
     * set the state and points of the score
     *
     * @param state  state of tile
     * @param points number of points
     */
    public void setScore(TileState state, int points) {
        scoreMap.put(state, points);
    }

    /**
     * returns TileStates current score
     *
     * @return string of state and score
     */
    public String toString() {
        String s = "";

        for (TileState state : scoreMap.keySet()) {
            s += state + ": " + scoreMap.get(state) + " | ";
        }

        return s;
    }

}
