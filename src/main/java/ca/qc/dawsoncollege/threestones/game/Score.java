package ca.qc.dawsoncollege.threestones.game;

import java.util.HashMap;

public class Score {
	private HashMap<TileState, Integer> scoreMap = new HashMap<TileState, Integer>();
	
	/*
	 
		for(TileState state: states) {
			scoreMap.put(state, 0);
		}
	}
	//*/
	
	public void setScore(TileState state, int points) {
		scoreMap.put(state, points);
	}
	
	public int getScore(TileState state) {
		return scoreMap.get(state);
	}
	
	public String toString() {
		String s = "";
		
		for(TileState state: scoreMap.keySet()) {
			s += state + ": " + scoreMap.get(state) + "\n";
		}
		return s;
	}
}
