package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.univangers.vajin.SnakeRPG;

import javax.xml.soap.Text;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 Écran permettant de se connecter directement sur un serveur.
 */
public class DirectConnectionScreen implements Screen {

    private final Stage stage;
    private final SnakeRPG game;

    private DatagramSocket socket; //UDP socket

    private DirectConnectionScreenState currentState;

    private WaitingForResponseState waitingForResponseState = new WaitingForResponseState();
    private InitialState initialState = new InitialState();

    int timeoutPeriod; //Time we wait before stoping to try to connect to a given server.


    public DirectConnectionScreen(SnakeRPG game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
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
        this.currentState = this.initialState;

        Skin skin = game.getUISkin();


        Table table = new Table(skin);
        table.setFillParent(true);
        table.debugAll();

        stage.addActor(table);

        TextField ipTextField = new TextField("127.0.0.0", skin);

        TextField portTextField = new TextField("7989", skin);

        TextButton connect = new TextButton("Connect", skin);
        TextButton backToMenu = new TextButton("Back", skin);

        table.add(ipTextField).colspan(2);
        table.row().pad(10, 0, 10, 0);
        table.add(portTextField).colspan(2);
        table.row();
        table.add(connect);
        table.add(backToMenu);

        backToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        stage.dispose();
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
