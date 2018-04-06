package fr.vajin.snakerpg.gameroom.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fr.univangers.vajin.IO.TileMapReader;
import fr.univangers.vajin.engine.EngineBuilder;
import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.WrongPlayersNumberException;
import fr.univangers.vajin.engine.entities.snake.SimpleSnake;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.gameroom.Cleaner;
import fr.vajin.snakerpg.gameroom.Controller;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
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
    public synchronized UserEntity acceptConnection(int userId, byte[] token, InetAddress inetAddress, int port) {

        userId = ++this.lastId;
        SecureRandom random = new SecureRandom();
        token = new byte[4];
        random.nextBytes(token);

        String adrStr = inetAddress.toString() + ":" + port;

        if(idAddressMap.inverse().containsKey(adrStr)){
            return null;
        }

        idAddressMap.put(userId, adrStr);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setAlias("user"+String.valueOf(userId));
        userEntity.setAccountName("account"+String.valueOf(userId));
        userEntity.setToken(token);

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
    public Collection<GameParticipationEntity> getLastGameResults() {
        return null;
    }

    @Override
    public String getMapName() {
        return this.map;
    }

    @Override
    public void startGame() {

        TileMapReader reader = TileMapReader.newTileMapReader(map);
        EngineBuilder gameEngineBuilder = new EngineBuilder(reader.getField(), gameMode.getId());

        for (PlayerHandler playerHandler : playerHandlers){

            int id = playerHandler.getUserId();
            gameEngineBuilder.addSnake(id, new SimpleSnake());

        }
        try {
            this.gameEngine = gameEngineBuilder.build();
        } catch (WrongPlayersNumberException e) {
            e.printStackTrace();
        }

        for (PlayerHandler playerHandler : this.playerHandlers){
            playerHandler.getPlayerPacketCreator().startGame();
            playerHandler.getPlayerPacketCreator().setState(PlayerPacketCreator.GAME_START);
        }

        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.schedule(new GameRun(gameEngine,32), 10, TimeUnit.SECONDS);
    }

    @Override
    public void setPlayerReady(int idPlayer) {

        if (!idPlayersReady.contains(idPlayer)){
            idPlayersReady.add(idPlayer);
        }

        if (idPlayersReady.size()==playerHandlers.size()){
            this.startGame();
        }



    }
}
