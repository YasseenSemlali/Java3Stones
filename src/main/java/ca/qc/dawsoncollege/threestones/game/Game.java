package ca.qc.dawsoncollege.threestones.game;

import ca.qc.dawsoncollege.threestones.game.Player.Player;

public class Game {
	
	public void run(Player p1, Player p2) {
		this.sendBoardInfo();
		Board board = new Board();
		
		while(true) {
			Move m1 = p1.getMove();
			while(!board.checkIfValidMove(m1)) {
				sendValidMove(false);
				m1 = p1.getMove();
			}
			sendValidMove(true);
			board.play(m1);
			

			Move m2 = p2.getMove();
			while(!board.checkIfValidMove(m2)) {
				sendValidMove(false);
				m2 = p2.getMove();
			}
			sendValidMove(true);
			board.play(m2);
			
		}
	}
	
	private void sendBoardInfo() {
		
	}
	
	private void sendValidMove(boolean valid) {
		
	}
}
