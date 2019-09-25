package ca.qc.dawsoncollege.threestones.game;

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
}
