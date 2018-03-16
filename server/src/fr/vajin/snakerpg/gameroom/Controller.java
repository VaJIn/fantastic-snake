package fr.vajin.snakerpg.gameroom;

import fr.univangers.vajin.engine.GameEngine;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import java.util.Collection;

public interface Controller {

    int ID_PROTOCOL = 0x685fa053;

    int getCurrentPlayerCount();

    /**
     * @return max number of player that can be simultaneousy connected
     */
    int getGameRoomSize();

    GameEngine getCurrentEngine();

//    void addPlayerWaitingForConnection(UserEntity userEntity);
//
void addPlayerHandler(PlayerHandler playerHandler);
//
//    UserEntity acceptConnection(int userId, byte[] token);

    GameModeEntity currentGameMode();

    Collection<GameParticipationEntity> getLastGameResults();

}
