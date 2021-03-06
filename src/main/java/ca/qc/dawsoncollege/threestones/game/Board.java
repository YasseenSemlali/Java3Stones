package ca.qc.dawsoncollege.threestones.game;

public class Board {
	public static final int HEIGHT = 11;
	public static final int WIDTH = 11;

	public static final int NUM_PIECES = 15;
	public static final int STONES_FOR_POINT = 3;

	private Tile[][] grid;

	int lastPlayedX = -1;
	int lastPlayedY = -1;

	public static void main(String[] args) {
		Board b = new Board();
		System.out.println(b + "\n");
	}

	public Board() {
		this.grid = new Tile[WIDTH][HEIGHT];
		this.populateGrid();
	}

	private void populateGrid() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				this.grid[x][y] = new Tile(x, y, TileState.EMPTY);
			}
		}
	}

	public Tile get(int x, int y) {
		return this.grid[x][y];
	}

	public void play(Move move) {
		int x = move.getX();
		int y = move.getY();

		if (!checkIfValidMove(move))
			throw new IllegalArgumentException("Invalid move");
		if (move.getState() == TileState.EMPTY)
			throw new IllegalArgumentException("Invalid state");

		this.get(x, y).setTileState(move.getState());
		this.lastPlayedX = x;
		this.lastPlayedY = y;
	}

	public boolean checkIfValidMove(Move move) {
		int x = move.getX();
		int y = move.getY();
		return this.get(x, y).isEmpty() && x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT && this.validateLastPlayed(x, y);
	}

	public Score calculateScore() {
		Score score = new Score();
		for (TileState state : TileState.getPlayablePieces()) {
			int points = 0;

			// Get horizontal
			for (int x = 0; x < WIDTH - Board.STONES_FOR_POINT; x++) {
				for (int y = 0; y < HEIGHT; y++) {
					if(this.get(x, y).getState() == state &&
							this.get(x + 1, y).getState() == state &&
							this.get(x + 2, y).getState() == state) {
						points++;
					}
				}
			}

			// Get vertical
			for (int x = 0; x < WIDTH; x++) {
				for (int y = 0; y < HEIGHT - Board.STONES_FOR_POINT; y++) {
					if(this.get(x, y).getState() == state &&
							this.get(x, y + 1).getState() == state &&
							this.get(x, y + 2).getState() == state) {
						points++;
					}
				}
			}

			// Get diagonal right
			for (int x = 0; x < WIDTH - Board.STONES_FOR_POINT; x++) {
				for (int y = 0; y < HEIGHT - Board.STONES_FOR_POINT; y++) {
					if(this.get(x, y).getState() == state &&
							this.get(x + 1, y + 2).getState() == state &&
							this.get(x + 1, y + 2).getState() == state) {
						points++;
					}
				}
			}

			// Get diagonal left
			for (int x = 0; x < WIDTH - Board.STONES_FOR_POINT; x++) {
				for (int y = HEIGHT - Board.STONES_FOR_POINT -1; y >= 0; y--) {
					if(this.get(x, y).getState() == state &&
							this.get(x + 1, y + 2).getState() == state &&
							this.get(x + 1, y + 2).getState() == state) {
						points++;
					}
				}
			}
			
			score.setScore(state, points);
		}

		return score;
	}

	private boolean validateLastPlayed(int x, int y) {
		if (this.lastPlayedX == -1 && this.lastPlayedY == -1)
			return true;

		if (colFree(x) && rowFree(y)) {
			return x == this.lastPlayedX || y == this.lastPlayedY;
		}

		return true;
	}

	private boolean colFree(int x) {
		for (int y = 0; y < WIDTH; y++) {
			if (this.get(x, y).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private boolean rowFree(int y) {
		for (int x = 0; x < HEIGHT; x++) {
			if (this.get(x, y).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int j = HEIGHT - 1; j >= 0; j--) {
			for (int i = 0; i < WIDTH; i++) {
				sb.append(this.get(i, j));
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
