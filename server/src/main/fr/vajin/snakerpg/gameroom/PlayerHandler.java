package fr.vajin.snakerpg.gameroom;


import fr.vajin.snakerpg.database.entities.UserEntity;

public interface PlayerHandler {

    int getUserId();

    int getUserToken();

    UserEntity getUserEntity();

    Controller getController();

    PlayerPacketHandler getPlayerPacketHandler();

    PlayerPacketCreator getPlayerTransmitter();

    void aliveSignalReceive();

    long getLastAliveSignalReceived();

}
