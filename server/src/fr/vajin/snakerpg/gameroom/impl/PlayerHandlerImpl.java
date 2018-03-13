package fr.vajin.snakerpg.gameroom.impl;

import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;
import fr.vajin.snakerpg.gameroom.PlayerTransmiter;

public class PlayerHandlerImpl implements PlayerHandler {

    int userId;
    byte[] userToken;

    PlayerPacketHandler playerPacketHandler;
    PlayerTransmiter playerTransmiter;
    PlayerPacketCreator playerPacketCreator;

    public PlayerHandlerImpl(int userId,
                             byte[] userToken,
                             PlayerPacketHandler playerPacketHandler,
                             PlayerTransmiter playerTransmiter,
                             PlayerPacketCreator playerPacketCreator) {
        this.userId = userId;
        this.userToken = userToken;
        this.playerPacketHandler = playerPacketHandler;
        this.playerTransmiter = playerTransmiter;
        this.playerPacketCreator = playerPacketCreator;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public byte[] getUserToken() {
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
}
