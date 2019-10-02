package ca.qc.dawsoncollege.threestones.game;

public class Board {
	public static int HEIGHT = 11;
	public static int WIDTH = 11;
	
	private Tile[][] grid;
	
	int lastPlayedX = -1;
	int lastPlayedY = -1;
	
	public static void main(String[] args) {
		Board b = new Board();
		System.out.println(b+"\n");
	}
	
	public Board() {
		this.grid = new Tile[WIDTH][HEIGHT];
		this.populateGrid();
	}
	
	private void populateGrid() {
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				this.grid[i][j] = new Tile(i, j, TileState.EMPTY);
			}
		}
	}
	
	public Tile get(int x, int y) {
		return this.grid[x][y];
	}
	
	public void play(Move move) {
		int x = move.getX();
		int y = move.getY();
		
		if(!checkIfValidMove(move)) throw new IllegalArgumentException("Invalid move");
		if(move.getState() == TileState.EMPTY) throw new IllegalArgumentException("Invalid state");
		
		this.get(x,y).setTileState(move.getState());
		this.lastPlayedX = x;
		this.lastPlayedY = y;
	}
	
	public boolean checkIfValidMove(Move move) {
		int x = move.getX();
		int y = move.getY();
		return this.get(x,y).isEmpty() 
				&& x >= 0 && x < WIDTH  && y >= 0 && y < HEIGHT 
				&& this.validateLastPlayed(x, y);
				
	}
	
	private boolean validateLastPlayed(int x, int y) {
		if(this.lastPlayedX == -1 && this.lastPlayedY == -1) return true;
		
		if(colFree(x) && rowFree(y)) {
			return x == this.lastPlayedX || y == this.lastPlayedY;
		}
		
		return true;
	}
	
	private boolean colFree(int x) {
		for(int y = 0; y < WIDTH; y++) {
			if(this.get(x, y).isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean rowFree(int y) {
		for(int x = 0; x < HEIGHT; x++) {
			if(this.get(x, y).isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int j = HEIGHT -1; j >= 0; j--) {
			for(int i = 0; i < WIDTH; i++) {
				sb.append(this.grid[i][j]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}