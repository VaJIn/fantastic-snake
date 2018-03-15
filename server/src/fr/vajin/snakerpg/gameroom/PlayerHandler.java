package fr.vajin.snakerpg.gameroom;

public interface PlayerHandler {

    int getUserId();

    int getUserToken();

    PlayerPacketHandler getPlayerPacketHandler();

    PlayerPacketCreator getPlayerTransmitter();

    void aliveSignalReceive();

    long getLastAliveSignalReceived();

}
