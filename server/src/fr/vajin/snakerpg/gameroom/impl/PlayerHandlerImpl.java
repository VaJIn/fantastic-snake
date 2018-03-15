package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;

import java.time.Instant;

public class PlayerHandlerImpl implements PlayerHandler {

    int userId;
    int userToken;

    PlayerPacketHandler playerPacketHandler;
    PlayerTransmiter playerTransmiter;
    PlayerPacketCreator playerPacketCreator;

    private long lastAliveSignalReceived;

    public PlayerHandlerImpl(int userId,
                             int userToken,
                             PlayerPacketHandler playerPacketHandler,
                             PlayerTransmiter playerTransmiter,
                             PlayerPacketCreator playerPacketCreator) {
        this.userId = userId;
        this.userToken = userToken;
        this.playerPacketHandler = playerPacketHandler;
        this.playerTransmiter = playerTransmiter;
        this.playerPacketCreator = playerPacketCreator;

        lastAliveSignalReceived = Instant.now().toEpochMilli();
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public int getUserToken() {
        return userToken;
    }

    @Override
    public PlayerPacketHandler getPlayerPacketHandler() {
        return playerPacketHandler;
    }

    @Override
    public PlayerPacketCreator getPlayerTransmitter() {
        return playerPacketCreator;
    }

    @Override
    public void aliveSignalReceive() {
        lastAliveSignalReceived = Instant.now().toEpochMilli();
    }

    @Override
    public long getLastAliveSignalReceived() {
        return this.lastAliveSignalReceived;
    }
}
