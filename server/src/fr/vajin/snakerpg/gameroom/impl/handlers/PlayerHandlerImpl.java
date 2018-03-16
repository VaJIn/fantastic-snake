package fr.vajin.snakerpg.gameroom.impl.handlers;

import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;
import fr.vajin.snakerpg.gameroom.impl.PlayerTransmiter;

import java.time.Instant;

public class PlayerHandlerImpl implements PlayerHandler {

    private int userId;
    private int userToken;

    private PlayerPacketHandler playerPacketHandler;
    private PlayerTransmiter playerTransmiter;
    private PlayerPacketCreator playerPacketCreator;

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
    public synchronized void aliveSignalReceive() {
        lastAliveSignalReceived = Instant.now().toEpochMilli();
    }

    @Override
    public synchronized long getLastAliveSignalReceived() {
        return this.lastAliveSignalReceived;
    }
}
