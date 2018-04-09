package fr.univangers.vajin;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.impl.NetworkControllerImpl;
import fr.univangers.vajin.screens.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramSocket;
import java.net.SocketException;

public class SnakeRPG extends Game implements ApplicationListener {

    private final static Logger logger = LogManager.getLogger(SnakeRPG.class);

    private final static String UISkinAtlas = "skin/clean-crispy-ui.atlas";
    private final static String UISkinJSON = "skin/clean-crispy-ui.json";

    private SnakeRPGAssetManager assetManager;

    private GameLoadingScreen gameLoadingScreen;
    private DirectConnectionScreen directConnectionScreen;
    private HostLobbyScreen hostLobbyScreen;
    private DistantLobbyScreen distantLobbyScreen;
    private MenuScreen menuScreen;
    private LocalGameScreen localGameScreen;
    private EndGameScreen endGameScreen;

    private Skin UISkin;

    private NetworkController networkController;
    private DatagramSocket datagramSocket;
    private CreditScreen creditScreen;
    private DistantGameScreen distantGameScreen;
    public static final int DISTANT_GAME_SCREEN = 8;

    private AppPreferences appPreferences;

    public static final int MENU_SCREEN = 1;
    public static final int DIRECT_CONNECTION_SCREEN = 2;
    public static final int GAME_LOADING_SCREEN = 3;
    public static final int GAME_SCREEN = 4;
    public static final int HOST_LOBBY_SCREEN = 5;
    public static final int CREDIT_SCREEN = 6;
    public static final int DISTANT_LOBBY_SCREEN = 7;
    public static final int OPTION_SCREEN = 9;
    private OptionScreen optionScreen;

    @Override
    public void create() {
        String mapFileName = "simple_map.tmx";

        this.appPreferences = new AppPreferences();

        this.assetManager = new SnakeRPGAssetManager();

        assetManager.queueUIAssets();
        assetManager.getManager().finishLoading();

        this.UISkin = new Skin(Gdx.files.internal(UISkinJSON), assetManager.getManager().get(UISkinAtlas, TextureAtlas.class));

        this.menuScreen = new MenuScreen(this);

        this.gameLoadingScreen = new GameLoadingScreen(this);

        this.setScreen(menuScreen);
    }

    public void changeScreen(int screen) {
        switch (screen) {
            case MENU_SCREEN:
                if (this.menuScreen == null) {
                    this.menuScreen = new MenuScreen(this);
                }
                logger.debug("Change screen to MenuScreen");
                this.setScreen(this.menuScreen);
                break;
            case DIRECT_CONNECTION_SCREEN:
                if (this.directConnectionScreen == null) {
                    this.directConnectionScreen = new DirectConnectionScreen(this);
                }
                logger.debug("Change screen to DirectConnectionScreen");
                this.setScreen(directConnectionScreen);
                break;
            case GAME_LOADING_SCREEN:
                if (this.gameLoadingScreen == null) {
                    this.gameLoadingScreen = new GameLoadingScreen(this);
                }
                logger.debug("Change screen to GameLoadingScreen");
                this.setScreen(gameLoadingScreen);
                break;
            case HOST_LOBBY_SCREEN:
                if (this.hostLobbyScreen == null) {
                    this.hostLobbyScreen = new HostLobbyScreen(this, getNetworkController());
                }
                logger.debug("Change screen to HostLobbyScreen");
                this.setScreen(hostLobbyScreen);
                break;
            case CREDIT_SCREEN:
                if (this.creditScreen == null) {
                    this.creditScreen = new CreditScreen(this);
                }
                logger.debug("Change screen to CreditScreen");
                this.setScreen(creditScreen);
                break;
            case DISTANT_LOBBY_SCREEN:
                if (this.distantLobbyScreen == null) {
                    this.distantLobbyScreen = new DistantLobbyScreen(this, getNetworkController());
                }
                logger.debug("Change screen to DistantLobbyScreen");
                this.setScreen(distantLobbyScreen);
                break;
            case DISTANT_GAME_SCREEN:
                if (this.distantGameScreen == null) {
                    this.distantGameScreen = new DistantGameScreen(this);
                }
                logger.debug("Change screen to DistantGameScreen");
                this.setScreen(this.distantGameScreen);
                break;
            case OPTION_SCREEN:
                if (this.optionScreen == null) {
                    this.optionScreen = new OptionScreen(this);
                }
                this.setScreen(this.optionScreen);
                break;
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public HostLobbyScreen getHostLobbyScreen() {
        return hostLobbyScreen;
    }

    public LocalGameScreen getLocalGameScreen() {
        return localGameScreen;
    }

    public GameLoadingScreen getGameLoadingScreen() {
        if (gameLoadingScreen == null) {
            this.gameLoadingScreen = new GameLoadingScreen(this);
        }
        return gameLoadingScreen;
    }

    public DirectConnectionScreen getDirectConnectionScreen() {
        return directConnectionScreen;
    }

    public SnakeRPGAssetManager getAssetManager() {
        return assetManager;
    }

    public NetworkController getNetworkController() {
        if(networkController==null){
            try {
                networkController = new NetworkControllerImpl(this, this.openSocket());
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return networkController;
    }

    public Skin getUISkin() {
        return UISkin;
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public DatagramSocket openSocket(int port) throws SocketException {
        if (this.datagramSocket == null || this.datagramSocket.isClosed()) {
            this.datagramSocket = new DatagramSocket(port);
        }
        return this.datagramSocket;
    }

    public DatagramSocket openSocket() throws SocketException {
        if (this.datagramSocket == null || this.datagramSocket.isClosed()) {
            this.datagramSocket = new DatagramSocket();
            logger.info("Socket opened on port " + this.datagramSocket.getLocalPort());
        }
        return this.datagramSocket;
    }

    public DistantLobbyScreen getDistantLobbyScreen() {
        if(this.distantLobbyScreen == null){
            this.distantLobbyScreen = new DistantLobbyScreen(this, this.getNetworkController());
        }
        return distantLobbyScreen;
    }

    public DistantGameScreen getDistantGameScreen() {
        if (distantGameScreen == null) {
            this.distantGameScreen = new DistantGameScreen(this);
        }
        return distantGameScreen;
    }


    public EndGameScreen getEndLoadingScreen() {
        if (endGameScreen == null){
            this.endGameScreen = new EndGameScreen(this);
        }
        return endGameScreen;
    }

    public AppPreferences getAppPreferences() {
        return appPreferences;
    }

}
