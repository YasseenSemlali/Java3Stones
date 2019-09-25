package ca.qc.dawsoncollege.threestones.game;

import java.util.Arrays;

public enum TileState {
	EMPTY, BLACK, WHITE;
	
	public String toString() {
		switch(this) {
		case BLACK:
			return "B";
		case EMPTY:
			return "E";
		case WHITE:
			return "W";
		default:
			return " ";
		}
	}
	
	public boolean isPlayable() {
		return Arrays.stream(getPlayablePieces()).anyMatch(this::equals);
	}
	
	public static TileState[] getPlayablePieces() {
		return new TileState[] {WHITE, BLACK};
	}
}
