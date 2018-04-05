package fr.vajin.snakerpg.gameroom;

import fr.univangers.vajin.engine.GameEngine;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.net.InetAddress;
import java.util.Collection;

public interface Controller {

    int getCurrentPlayerCount();

    /**
     * @return max number of player that can be simultaneousy connected
     */
    int getGameRoomSize();

    GameEngine getCurrentEngine();

    void addPlayerWaitingForConnection(UserEntity userEntity);
//
void addPlayerHandler(PlayerHandler playerHandler);
//

    void removePlayer(PlayerHandler playerHandler);

    UserEntity acceptConnection(int userId, byte[] token, InetAddress inetAddress, int port);

    GameModeEntity currentGameMode();

    Collection<PlayerHandler> getPlayerHandlers();

    Collection<GameParticipationEntity> getLastGameResults();

    String getMapName();

    void startGame();


}
