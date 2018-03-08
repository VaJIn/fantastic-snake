package fr.vajin.snakerpg.gameroom;

import fr.vajin.snakerpg.database.entities.UserEntity;

public interface Controller {

    void addPlayerWaitingForConnection(UserEntity userEntity);

    void addPlayerHandler(PlayerHandler playerHandler);

    UserEntity acceptConnection(int userId, byte[] token);
}
