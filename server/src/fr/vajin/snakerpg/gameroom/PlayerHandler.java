package fr.vajin.snakerpg.gameroom;

public interface PlayerHandler {

    int getUserId();

    byte[] getUserToken();

    PlayerPacketHandler getPlayerPacketHandler();

    PlayerPacketCreator getPlayerTransmitter();

    void aliveSignalReceive();

    long getLastAliveSignalReceived();

}
