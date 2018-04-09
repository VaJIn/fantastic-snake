package fr.univangers.vajin.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import fr.univangers.vajin.SnakeRPG;

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

        this.acceptedConnection = false;
        this.currentState = this.initialState;

        Skin skin = this.getApplication().getUISkin();

        Table table = new Table(skin);
        table.setFillParent(true);

        this.getStage().addActor(table);

        String lastServerName = this.getApplication().getAppPreferences().getLastServerName();
        if (lastServerName == null) {
            lastServerName = "";
        }

        int lastPort = this.getApplication().getAppPreferences().getLastServerPort();
        String lastPortStr = lastPort != 0 ? String.valueOf(lastPort) : "";

        String alias = this.getApplication().getAppPreferences().getLocalAlias();
        if (alias == null || alias.trim().isEmpty()) {
            alias = "";
        }

        TextField ipTextField = new TextField(lastServerName, skin);
        ipTextField.setAlignment(Align.right);
        TextField portTextField = new TextField(lastPortStr, skin);
        portTextField.setAlignment(Align.right);
        TextField userNameTextField = new TextField(alias, skin);
        userNameTextField.setAlignment(Align.center);

        TextButton connect = new TextButton("Connect", skin);
        TextButton backToMenu = new TextButton("Back", skin);

        table.add(new Label("IP / Name", skin));
        table.add(ipTextField).colspan(2).fillX().pad(0, 5, 0, 5);
        table.row().pad(10, 0, 10, 0);
        table.add(new Label("Port", skin));
        table.add(portTextField).colspan(2).fillX().pad(0, 5, 0, 5);
        table.row().pad(10, 0, 10, 0);
        table.add(new Label("Alias", skin));
        table.add(userNameTextField).fillX().colspan(2).pad(0, 5, 0, 5);
        table.row();
        table.add(backToMenu).fillX();
        table.add(connect).fillX().colspan(2);

        backToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        connect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SnakeRPG application = getApplication();

                try {
                    InetAddress address = InetAddress.getByName(ipTextField.getText());
                    int port = Integer.valueOf(portTextField.getText());
                    String alias = userNameTextField.getText();

                    getApplication().getAppPreferences().setLastServer(ipTextField.getText(), port);
                    getApplication().getAppPreferences().setLocalAlias(alias);

                    if (getApplication().getAppPreferences().hasChanged()) {
                        getApplication().getAppPreferences().flush();
                    }

                    waitingForResponseState.connectTo(address,port);
                    setState(waitingForResponseState);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(acceptedConnection){
            this.getApplication().changeScreen(SnakeRPG.DISTANT_LOBBY_SCREEN);
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
            getApplication().getNetworkController().connect(address, port);
            getApplication().getNetworkController().startReceiver();
        }

        @Override
        public synchronized void acceptedConnection(InetAddress address, int port) {
            if (address.equals(waitedAddress) && port == waitedPort) {
                System.out.println("Connexion acceptée");
                getApplication().getNetworkController().setCurrentServer(waitedAddress, waitedPort);
                getApplication().getNetworkController().startTransmiter();
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
