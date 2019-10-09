package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.GameController;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        GameController session = new GameController();
        session.run();

//    	Player p2 = new AIPlayer();
//
//        Game game = new Game();
//        game.run(p1, p2);
    }
}
