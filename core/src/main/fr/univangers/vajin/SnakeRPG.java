package fr.univangers.vajin;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.impl.NetworkControllerImpl;
import fr.univangers.vajin.screens.*;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.jsondatabeans.LobbyBean;
import fr.vajin.snakerpg.jsondatabeans.PlayerBean;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;

public class SnakeRPG extends Game implements ApplicationListener {

    private final static String UISkinAtlas = "skin/clean-crispy-ui.atlas";
    private final static String UISkinJSON = "skin/clean-crispy-ui.json";

    SnakeRPGAssetManager assetManager;

    private GameScreen gameScreen;
    private GameLoadingScreen gameLoadingScreen;
    private DirectConnectionScreen directConnectionScreen;
    private HostLobbyScreen hostLobbyScreen;
    private DistantLobbyScreen distantLobbyScreen;
    private MenuScreen menuScreen;

    private Skin UISkin;

    NetworkController networkController;
    private DatagramSocket datagramSocket;
    private CreditScreen creditScreen;

    @Override
    public void create() {


        String mapFileName = "simple_map.tmx";

        this.assetManager = new SnakeRPGAssetManager();

        assetManager.queueUIAssets();
        assetManager.getManager().finishLoading();

        Map<String, Class> filesToLoad = Maps.newHashMap();

        filesToLoad.put(mapFileName, TiledMap.class);
        filesToLoad.put(GameConstants.ATLAS_FILENAME, TextureAtlas.class);


        this.UISkin = new Skin(Gdx.files.internal(UISkinJSON), assetManager.getManager().get(UISkinAtlas, TextureAtlas.class));

        LobbyBean lobbyBean = new LobbyBean();
        GameModeEntity gameModeEntity = new GameModeEntity(1, "Classic Deathmatch", 2, 8);
        lobbyBean.setGameMode(gameModeEntity);
        lobbyBean.setMap("sample_map");


        lobbyBean.setPlayers(
                ImmutableList.of(
                        new PlayerBean(1, "Jean Paul", 1),
                        new PlayerBean(2, "Kirikoo", 2),
                        new PlayerBean(3, "Superman", 3)
                )
        );

        this.hostLobbyScreen = new HostLobbyScreen(this);
        hostLobbyScreen.setLobbyBean(lobbyBean);

        this.menuScreen = new MenuScreen(this);

        this.setScreen(menuScreen);
    }


    public static final int MENU_SCREEN = 1;
    public static final int DIRECT_CONNECTION_SCREEN = 2;
    public static final int GAME_LOADING_SCREEN = 3;
    public static final int GAME_SCREEN = 4;
    public static final int HOST_LOBBY_SCREEN = 5;
    public static final int CREDIT_SCREEN = 6;
    public static final int DISTANT_LOBBY_SCREEN = 7;

    public void changeScreen(int screen) {
        switch (screen) {
            case MENU_SCREEN:
                if (this.menuScreen == null) {
                    this.menuScreen = new MenuScreen(this);
                }
                this.setScreen(this.menuScreen);
                break;
            case DIRECT_CONNECTION_SCREEN:
                if (this.directConnectionScreen == null) {
                    this.directConnectionScreen = new DirectConnectionScreen(this);
                }
                this.setScreen(directConnectionScreen);
                break;
            case GAME_LOADING_SCREEN:
                if (this.gameLoadingScreen == null) {
//                    this.gameLoadingScreen = new GameLoadingScreen(this, assetManager, ) //TODO
                }
                this.setScreen(gameLoadingScreen);
                break;
            case HOST_LOBBY_SCREEN:
                if (this.hostLobbyScreen == null) {
                    this.hostLobbyScreen = new HostLobbyScreen(this);
                }
                this.setScreen(hostLobbyScreen);
                break;
            case CREDIT_SCREEN:
                if (this.creditScreen == null) {
                    this.creditScreen = new CreditScreen(this);
                }
                this.setScreen(creditScreen);
                break;
            case DISTANT_LOBBY_SCREEN:
                if (this.distantLobbyScreen == null) {
                    this.distantLobbyScreen = new DistantLobbyScreen(this);
                }
                this.setScreen(distantLobbyScreen);
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

    public GameScreen getGameScreen() {
        return gameScreen;
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
                networkController = new NetworkControllerImpl(this,new DatagramSocket());
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

    public DistantLobbyScreen getDistantLobbyScreen() {
        if(this.distantLobbyScreen == null){
            distantLobbyScreen = new DistantLobbyScreen(this);
        }
        return distantLobbyScreen;
    }
}
