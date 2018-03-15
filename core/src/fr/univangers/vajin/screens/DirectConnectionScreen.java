package fr.univangers.vajin.screens;

import com.badlogic.gdx.Screen;
import fr.univangers.vajin.SnakeRPG;

import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 Écran permettant de se connecter directement sur un serveur.
 */
public class DirectConnectionScreen implements Screen {

    private SnakeRPG game;

    private DatagramSocket socket; //UDP socket

    private DirectConnectionScreenState currentState;

    private WaitingForResponseState waitingForResponseState = new WaitingForResponseState();
    private InitialState initialState = new InitialState();

    int timeoutPeriod; //Time we wait before stoping to try to connect to a given server.


    public DirectConnectionScreen(SnakeRPG game, DatagramSocket socket, int timeoutPeriod) {
        this.game = game;
        this.socket = socket;
        this.timeoutPeriod = timeoutPeriod;
    }

    /**
     * Method called by the packet handler when the connection is accepted, from the server with the given adress and given port).
     *
     * @param address
     * @param port
     */
    public synchronized void acceptedConnection(InetAddress address, int port) {
        currentState.acceptedConnection(address, port);
    }

    public synchronized void refusedConnection(InetAddress address, int port) {
        currentState.refusedConnection(address, port);
    }

    private synchronized void connectToServer(InetAddress address, int port) {
        //TODO initiate connection with the given server
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //TODO
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    void goToLobbyScreen() {
        //TODO

    }

    void setState(DirectConnectionScreenState state) {
        this.currentState = state;
    }

    private class InitialState implements DirectConnectionScreenState {
        @Override
        public void connectTo(InetAddress address, int port) {

        }

        @Override
        public void acceptedConnection(InetAddress address, int port) {
            //Discard
        }

        @Override
        public void refusedConnection(InetAddress address, int port) {
            //Discard
        }
    }

    private class WaitingForResponseState implements DirectConnectionScreenState {

        InetAddress waitedAddress;
        int waitedPort;

        @Override
        public synchronized void connectTo(InetAddress address, int port) {
            this.waitedAddress = address;
            this.waitedPort = port;

            //TODO networking
        }

        @Override
        public synchronized void acceptedConnection(InetAddress address, int port) {
            if (address.equals(waitedAddress) && port == waitedPort) {
                //TODO go to next screen
            }//Else discard
        }

        @Override
        public synchronized void refusedConnection(InetAddress address, int port) {
            if (address.equals(waitedAddress) && port == waitedPort) {
                setState(initialState);
            }
        }
    }
}
