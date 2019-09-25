package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.Game;
import ca.qc.dawsoncollege.threestones.game.TileState;
import ca.qc.dawsoncollege.threestones.game.Player.RandomPlayer;
import ca.qc.dawsoncollege.threestones.game.Player.Player;


public class App 
{
    public static void main( String[] args )
    {
    	Player p1 = new RandomPlayer(TileState.WHITE);
    	Player p2 = new RandomPlayer(TileState.BLACK);
    	
        Game game = new Game();
        game.run(p1, p2);
    }
}
