package ca.qc.dawsoncollege.threestones.game.Network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Class handles all methods relating to sending and receiving from a connection
 *
 * @author Saad
 */
public class ThreeStonesConnector {

    private final static Logger LOG = LoggerFactory.getLogger(ThreeStonesConnector.class);
    private Socket servSocket;
    private InputStream in;
    private OutputStream out;

    /**
     * Primary Constructor for this class which takes an string and int in case of only having those
     * two available specifically in the client
     *
     * @param server takes a string representing the ip address of the server
     * @param port   takes an int that represents the port number
     * @throws IOException connection errors
     * @author Saad
     * @author Yasseen
     */
    public ThreeStonesConnector(String server, int port) throws IOException {
        try {
            this.servSocket = new Socket(server, port);
            in = servSocket.getInputStream();
            out = servSocket.getOutputStream();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Secondary constructor for this class used in the case of already having socket, specifically used in
     * in the Server
     *
     * @param player1 socket connection
     * @author Saad
     */
    public ThreeStonesConnector(Socket player1) {
        this.servSocket = player1;
        try {
            in = servSocket.getInputStream();
            out = servSocket.getOutputStream();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Method used to send data from one end to the other
     *
     * @param first  byte that represents the category of the send (Move, Quit, Play, Win, Tie)
     * @param second byte that represents the player that is doing the command
     * @param third  byte that represents the x to place piece or space by default
     * @param fourth byte that represents the y to place piece or space by default
     * @throws IOException when data is being sent
     * @author Saad
     */
    public void sendData(byte first, byte second, byte third, byte fourth) throws IOException {
        byte[] messages = {first, second, third, fourth};
        out.write(messages);
    }

    /**
     * Waits to receive data from one end of the connection
     *
     * @return byte[] of the data received
     * @throws IOException receives data being sent
     * @author Saad
     */
    public byte[] receiveData() throws IOException {
        byte[] receivedData = new byte[6];
        int receivedBytes = 0;
        while (receivedBytes < 6) {
            receivedBytes = in.read(receivedData);
        }
        return receivedData;
    }

    /**
     * Close the connection
     *
     * @throws IOException error when closing
     * @author Saad
     */
    public void closeSocket() throws IOException {
        servSocket.close();
    }

}
