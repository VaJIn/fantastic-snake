package fr.vajin.snakerpg.gameroom;

import fr.univangers.vajin.engine.GameEngine;

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
}
