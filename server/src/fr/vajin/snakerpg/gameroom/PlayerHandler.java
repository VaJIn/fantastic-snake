package fr.vajin.snakerpg.gameroom;

public interface PlayerHandler {

    int getUserId();

    int getUserToken();

    Controller getController();

    PlayerPacketHandler getPlayerPacketHandler();

    PlayerPacketCreator getPlayerTransmitter();

    void aliveSignalReceive();

    long getLastAliveSignalReceived();

}
