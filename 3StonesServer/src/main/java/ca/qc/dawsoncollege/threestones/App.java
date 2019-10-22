package ca.qc.dawsoncollege.threestones;

import ca.qc.dawsoncollege.threestones.game.Network.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class App {
    private final static Logger LOG = LoggerFactory.getLogger(GameSession.class);

    public static void main(String[] args) throws IOException {
        try {
            LOG.info("Looking for players!");
            ServerSocket servSock = new ServerSocket(5500);
            while (true) {
                try (Socket player1 = servSock.accept()) {
                    LOG.info("Player found");
                    GameSession gs = new GameSession(player1);
                    gs.run();
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
//        Game game = new Game();
//        game.run(p1, p2);
    }
}
