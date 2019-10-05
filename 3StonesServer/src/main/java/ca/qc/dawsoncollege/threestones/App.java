package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.Game;
import ca.qc.dawsoncollege.threestones.game.Network.GameSession;
import ca.qc.dawsoncollege.threestones.game.TileState;
import ca.qc.dawsoncollege.threestones.game.Player.RandomPlayer;
import ca.qc.dawsoncollege.threestones.game.Player.Player;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class App 
{
    public static void main( String[] args ) throws IOException {

        InetAddress ip = InetAddress.getLocalHost();

        GameSession session = new GameSession(new Socket(ip, 5500));
//        Game game = new Game();
//        game.run(p1, p2);
    }
}
