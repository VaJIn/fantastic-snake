package fr.vajin.snakerpg.gameroom;


import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.gameroom.impl.PlayerTransmiter;

public interface PlayerHandler {

    int getUserId();

    byte[] getUserToken();

    UserEntity getUserEntity();

    Controller getController();

    PlayerPacketHandler getPlayerPacketHandler();

    PlayerTransmiter getPlayerTransmitter();

    PlayerPacketCreator getPlayerPacketCreator();

    void aliveSignalReceive();

    long getLastAliveSignalReceived();

    void stopTransmiter();

    Snake getSnake();

    void setSnake(Snake snake);
}
