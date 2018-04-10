package fr.univangers.vajin;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
    public static final int GAME_END_SCREEN = 10;
    private OptionScreen optionScreen;

    @Override
    public void create() {

        this.appPreferences = new AppPreferences();

        this.assetManager = new SnakeRPGAssetManager(this);

        this.changeUISkin(this.getAppPreferences().getUISkin());

        this.menuScreen = new MenuScreen(this);

        this.gameLoadingScreen = new GameLoadingScreen(this);

        this.setScreen(menuScreen);
    }

    public void changeScreen(int screen) {
        switch (screen) {
            case MENU_SCREEN:
                logger.debug("Change screen to MenuScreen");
                this.setScreen(this.getMenuScreen());
                break;
            case DIRECT_CONNECTION_SCREEN:
                logger.debug("Change screen to DirectConnectionScreen");
                this.setScreen(this.getDirectConnectionScreen());
                break;
            case GAME_LOADING_SCREEN:
                logger.debug("Change screen to GameLoadingScreen");
                this.setScreen(this.getGameLoadingScreen());
                break;
            case HOST_LOBBY_SCREEN:
                logger.debug("Change screen to HostLobbyScreen");
                this.setScreen(this.getHostLobbyScreen());
                break;
            case CREDIT_SCREEN:
                logger.debug("Change screen to CreditScreen");
                this.setScreen(this.getCreditScreen());
                break;
            case DISTANT_LOBBY_SCREEN:
                logger.debug("Change screen to DistantLobbyScreen");
                this.setScreen(this.getDistantLobbyScreen());
                break;
            case DISTANT_GAME_SCREEN:
                logger.debug("Change screen to DistantGameScreen");
                this.setScreen(this.getDistantGameScreen());
                break;
            case OPTION_SCREEN:
                logger.debug("Change screen to OptionScreen");
                this.setScreen(this.getOptionScreen());
                break;
            case GAME_END_SCREEN:
                logger.debug("Change screen to EndGameScreen");
                this.setScreen(this.getEndGameScreen());
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
        if (this.directConnectionScreen == null) {
            this.directConnectionScreen = new DirectConnectionScreen(this);
        }
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

    public MenuScreen getMenuScreen() {
        if (menuScreen == null) {
            this.menuScreen = new MenuScreen(this);
        }
        return menuScreen;
    }

    public CreditScreen getCreditScreen() {
        if (creditScreen == null) {
            this.creditScreen = new CreditScreen(this);
        }
        return creditScreen;
    }

    public OptionScreen getOptionScreen() {
        if (this.optionScreen == null) {
            this.optionScreen = new OptionScreen(this);
        }
        return optionScreen;
    }

    public EndGameScreen getEndGameScreen() {
        if (endGameScreen == null){
            this.endGameScreen = new EndGameScreen(this);
        }
        return endGameScreen;
    }

    public void changeUISkin(String uiSkin) {

        logger.info("Setting UISkin to " + uiSkin);

        if (uiSkin == null || uiSkin.trim().isEmpty()) {
            uiSkin = "clean-crispy";
            this.getAppPreferences().setUISkin(uiSkin);
        }

        FileHandle skinDirectory = Gdx.files.internal("skin/" + uiSkin);

        logger.debug("skinDirectory.exist() ? " + skinDirectory.exists());

        if (!skinDirectory.exists()) {
            logger.error("No skin " + uiSkin + " found ! Defaulting to clean-crispy");
            skinDirectory = Gdx.files.internal("skin/clean-crispy");
        }

        for (FileHandle child : skinDirectory.list()) {
            logger.debug(child.name() + " - .atlas ? " + child.name().endsWith(".atlas"));
        }

        FileHandle[] atlas = skinDirectory.list((file, s) -> s.endsWith(".atlas"));
        FileHandle[] json = skinDirectory.list((file, s) -> s.endsWith(".json"));

        logger.debug("Found " + atlas.length + " atlas");
        logger.debug("Found " + json.length + " json");

//        if(atlas.length == 0){
//            throw new NoSuchElementException("No atlas found in skin directory " + uiSkin);
//        }
//        if(json.length == 0){
//            throw new NoSuchElementException("No json found in skin directory" + uiSkin);
//        }

        this.assetManager.setUiSkin(skinDirectory.name());
        this.assetManager.queueUIAssets();

        assetManager.queueUIAssets();
        assetManager.getManager().finishLoading();

        String UISkinJSON = json[0].path();
        String UISkinAtlas = atlas[0].path();

        this.UISkin = new Skin(Gdx.files.internal(UISkinJSON), assetManager.getManager().get(UISkinAtlas, TextureAtlas.class));
    }

    public AppPreferences getAppPreferences() {
        return appPreferences;
    }


}
