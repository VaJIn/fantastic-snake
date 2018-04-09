package fr.vajin.snakerpg.gameroom.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fr.univangers.vajin.IO.JSONFieldIO;
import fr.univangers.vajin.engine.EngineBuilder;
import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.WrongPlayersNumberException;
import fr.univangers.vajin.engine.entities.snake.SimpleSnake;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.engine.field.Field;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.gameroom.Cleaner;
import fr.vajin.snakerpg.gameroom.Controller;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.jsondatabeans.GameEndBean;
import fr.vajin.snakerpg.jsondatabeans.GameParticipationBean;
import fr.vajin.snakerpg.jsondatabeans.PlayerBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ControllerNoFilterImpl implements Controller{

    private static final Logger logger = LogManager.getLogger(ControllerNoFilterImpl.class);

    private GameModeEntity gameMode;
    private GameEngine gameEngine;
    private Collection<PlayerHandler> playerHandlers;
    private int lastId;
    private String map;
    private BiMap<Integer, String> idAddressMap;
    private Cleaner cleaner;
    private Collection<Integer> idPlayersReady;

    private int startGameDelay = 3;

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public ControllerNoFilterImpl(GameModeEntity gameMode, String map){
        this.gameMode = gameMode;
        this.gameEngine = null;
        this.playerHandlers = new ArrayList<>();
        this.lastId = 0;
        this.map = map;
        this.idAddressMap = HashBiMap.create();
        this.cleaner = new Cleaner(this, 15000);
        this.idPlayersReady = new ArrayList<>();
        cleaner.start();

        this.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public int getCurrentPlayerCount() {
        return playerHandlers.size();
    }

    @Override
    public int getGameRoomSize() {
        return gameMode.getMaxPlayer();
    }

    @Override
    public GameEngine getCurrentEngine() {
        return this.gameEngine;
    }

    @Override
    public void addPlayerWaitingForConnection(UserEntity userEntity) {
        //pass
    }

    @Override
    public void removePlayer(PlayerHandler playerHandler) {
        logger.info("Removed player " + playerHandler.getUserId() + " (" + idAddressMap.get(playerHandler.getUserId()) + ")");
        int id = playerHandler.getUserId();
        playerHandler.stopTransmiter();
        playerHandlers.remove(playerHandler);
        idAddressMap.remove(id);
    }

    @Override
    public void addPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandlers.add(playerHandler);
    }

    @Override
    public synchronized UserEntity acceptConnection(int userId, byte[] token, String alias, InetAddress inetAddress, int port) {
        userId = ++this.lastId;
        SecureRandom random = new SecureRandom();
        token = new byte[4];
        random.nextBytes(token);

        String adrStr = inetAddress.toString() + ":" + port;

        if(idAddressMap.inverse().containsKey(adrStr)){
            return null;
        }

        idAddressMap.put(userId, adrStr);

        if (alias == null || alias.trim().isEmpty()) {
            alias = "Unknown Player " + userId;
        }
        //TODO check if alias already used

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setAlias(alias);
        userEntity.setToken(token);

        logger.debug("Connection to player " + alias + ", id " + userId + " from " + inetAddress.toString() + ":" + port);

        return userEntity;
    }

    @Override
    public GameModeEntity currentGameMode() {
        return gameMode;
    }

    @Override
    public Collection<PlayerHandler> getPlayerHandlers() {
        return this.playerHandlers;
    }

    @Override
    public GameEndBean getLastGameResults() {

        if (gameEngine == null) {
            throw new NoSuchElementException("No last game to retrieve data from !");
        }

        GameEndBean gameEndBean = new GameEndBean();

        Collection<GameParticipationBean> gameParticipationBeanCollection = new ArrayList<>();

        for (PlayerHandler playerHandler : this.playerHandlers) {
            GameParticipationBean gameParticipationBean = new GameParticipationBean();

            PlayerBean playerBean = new PlayerBean();
            playerBean.setLocalId(playerHandler.getUserId());
            playerBean.setSnakeEntityId(playerHandler.getSnake().getEntityId());
            playerBean.setAlias(playerHandler.getUserEntity().getAlias());

            gameParticipationBean.setPlayer(playerBean);
            gameParticipationBean.setDeathCount(1);
            gameParticipationBean.setKillCount(0);

            gameParticipationBeanCollection.add(gameParticipationBean);
        }

        gameEndBean.setGameParticipations(gameParticipationBeanCollection);

        return gameEndBean;
    }

    @Override
    public String getMapName() {
        return this.map;
    }

    @Override
    public void startGame() {


//        TileMapReader reader = TileMapReader.newTileMapReader(map);

        logger.debug("Starting game");

        try {
            logger.debug("Loading field from files");
            Field fied = new JSONFieldIO().openStaticFieldJSON("map" + File.separator + map + ".json");

            logger.debug("Creating EngineBuilder");
            EngineBuilder gameEngineBuilder = new EngineBuilder(fied, gameMode.getId());


            logger.debug("Adding snakes");
            for (PlayerHandler playerHandler : playerHandlers) {
                int id = playerHandler.getUserId();
                Snake snake = new SimpleSnake();
                gameEngineBuilder.addSnake(id, new SimpleSnake());
                playerHandler.setSnake(snake);
            }

            logger.debug("Creating engine");
            this.gameEngine = gameEngineBuilder.build();

            logger.debug("Engine created");

            for (PlayerHandler playerHandler : this.playerHandlers) {
                logger.debug("Setting Game state for player" + playerHandler.getUserId());
                PlayerPacketCreator playerPacketCreator = playerHandler.getPlayerPacketCreator();
                playerPacketCreator.startGame();
                playerPacketCreator.setState(PlayerPacketCreator.GAME_STATE);
            }

            logger.debug("Scheduling start of the game in " + startGameDelay + " seconds");

            this.scheduledThreadPoolExecutor.schedule(new GameRun(gameEngine, this, 32), startGameDelay, TimeUnit.SECONDS);
        } catch (IOException e) {
            logger.error("Error opening map", e);
        } catch (WrongPlayersNumberException e) {
            e.printStackTrace();
            logger.error("Error creating engine", e);
        }
    }

    @Override
    public void endGame() {

        for(PlayerHandler playerHandler : this.playerHandlers){

            playerHandler.getPlayerPacketCreator().endGame();
            playerHandler.getPlayerPacketCreator().setState(PlayerPacketCreator.WAITING_FOR_GAME_STATE);

        }

    }

    @Override
    public void setPlayerReady(int idPlayer) {

        if (!idPlayersReady.contains(idPlayer)){
            idPlayersReady.add(idPlayer);
            logger.debug("Player " + idPlayer + " ready");
        }

        if (idPlayersReady.size()==playerHandlers.size()){
            this.startGame();
        }



    }
}
