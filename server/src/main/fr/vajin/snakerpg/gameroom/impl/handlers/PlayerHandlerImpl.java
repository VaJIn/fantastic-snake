package fr.vajin.snakerpg.gameroom.impl.handlers;

import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.gameroom.Controller;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;
import fr.vajin.snakerpg.gameroom.impl.PlayerTransmiter;
import fr.vajin.snakerpg.gameroom.impl.creators.PlayerPacketCreatorImpl;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;

public class PlayerHandlerImpl implements PlayerHandler {

    private int userId;
    private int userToken;

    private PlayerPacketHandler playerPacketHandler;
    private PlayerTransmiter playerTransmiter;
    private PlayerPacketCreator playerPacketCreator;

    private Controller controller;

    private long lastAliveSignalReceived;

    public PlayerHandlerImpl(DatagramSocket socket, InetAddress address, int port, Controller controller, int userId, int userToken) {
        this.controller = controller;

        this.userId = userId;
        this.userToken = userToken;

        this.playerPacketCreator = new PlayerPacketCreatorImpl(PlayerPacketCreator.ID_PROTOCOL);
        this.playerTransmiter = new PlayerTransmiter(socket,playerPacketCreator,PlayerPacketCreator.ID_PROTOCOL,2f,address,port);
        this.playerPacketHandler = new PlayerPacketHandlerImpl(playerPacketCreator,playerTransmiter,this.controller);
        this.playerPacketHandler.setPlayerHandler(this);
        lastAliveSignalReceived = Instant.now().toEpochMilli();

        playerTransmiter.start();
    }

    @Override
    public UserEntity getUserEntity() {
        return null; //TODO
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
    public Controller getController() {
        return this.controller;
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
