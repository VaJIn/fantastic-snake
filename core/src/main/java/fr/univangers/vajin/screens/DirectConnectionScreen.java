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
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.network.NetworkController;

import javax.xml.soap.Text;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/*
 Écran permettant de se connecter directement sur un serveur.
 */
public class DirectConnectionScreen extends AbstractMenuScreen {

private    boolean acceptedConnection = false;

    private DatagramSocket socket; //UDP socket

    private DirectConnectionScreenState currentState;

    private WaitingForResponseState waitingForResponseState = new WaitingForResponseState();
    private InitialState initialState = new InitialState();

    int timeoutPeriod = 10; //Time we wait before stoping to try to connect to a given server.


    public DirectConnectionScreen(SnakeRPG parent) {
        super(parent);

        this.currentState = initialState;
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
        super.show();
        this.currentState = this.initialState;

        Skin skin = this.getParent().getUISkin();


        Table table = new Table(skin);
        table.setFillParent(true);
        table.debugAll();

        this.getStage().addActor(table);

        TextField ipTextField = new TextField("HPC", skin);

        TextField portTextField = new TextField("1534", skin);

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
                getParent().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        connect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SnakeRPG parent = getParent();


                try {
                    InetAddress address = InetAddress.getByName(ipTextField.getText());
                    int port = Integer.valueOf(portTextField.getText());

                    waitingForResponseState.connectTo(address,port);
                    setState(waitingForResponseState);

                    parent.getNetworkController().connect(address, port);
                    parent.getNetworkController().startReceiver();

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        });
        Gdx.input.setInputProcessor(this.getStage());
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(acceptedConnection){
            this.getParent().changeScreen(SnakeRPG.DISTANT_LOBBY_SCREEN);
        }
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
                System.out.println("Connexion acceptée");
                getParent().getNetworkController().setCurrentServer(waitedAddress,waitedPort);
                getParent().getNetworkController().startTransmiter();
                acceptedConnection = true;
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
