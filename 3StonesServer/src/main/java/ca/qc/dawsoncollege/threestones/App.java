package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.Game;
import ca.qc.dawsoncollege.threestones.game.Player.AIPlayer;
import ca.qc.dawsoncollege.threestones.game.Player.NetworkPlayer;
import ca.qc.dawsoncollege.threestones.game.Player.Player;


public class App 
{
    public static void main( String[] args )
    {
    	Player p1 = new NetworkPlayer();
    	Player p2 = new AIPlayer();
    	
        Game game = new Game();
        game.run(p1, p2);
    }
}
