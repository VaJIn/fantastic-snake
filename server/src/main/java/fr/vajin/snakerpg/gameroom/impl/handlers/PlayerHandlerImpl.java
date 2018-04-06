package fr.vajin.snakerpg.gameroom.impl.handlers;

import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.gameroom.Controller;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.PlayerPacketHandler;
import fr.vajin.snakerpg.gameroom.impl.PlayerTransmiter;
import fr.vajin.snakerpg.gameroom.impl.creators.PlayerPacketCreatorImpl;
import fr.vajin.snakerpg.gameroom.impl.creators.WaitingForGameState;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;

public class PlayerHandlerImpl implements PlayerHandler {

    private int userId;
    private byte[] userToken;

    private PlayerPacketHandler playerPacketHandler;
    private PlayerTransmiter playerTransmiter;
    private PlayerPacketCreator playerPacketCreator;

    private Controller controller;

    private UserEntity userEntity;

    private long lastAliveSignalReceived;

    public PlayerHandlerImpl(DatagramSocket socket, InetAddress address, int port, Controller controller, int userId, byte[] userToken, UserEntity userEntity) {
        this.controller = controller;

        this.userId = userId;
        this.userToken = userToken;
        this.userEntity = userEntity;

        this.playerPacketCreator = new PlayerPacketCreatorImpl(PlayerPacketCreator.ID_PROTOCOL, this);
        this.playerTransmiter = new PlayerTransmiter(socket,playerPacketCreator,PlayerPacketCreator.ID_PROTOCOL,0.5f,address,port);
        this.playerPacketHandler = new PlayerPacketHandlerImpl(playerPacketCreator,playerTransmiter,this.controller);
        this.playerPacketHandler.setPlayerHandler(this);
        lastAliveSignalReceived = Instant.now().toEpochMilli();

        playerTransmiter.start();
    }

    @Override
    public UserEntity getUserEntity() {
        return this.userEntity;
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
    public Controller getController() {
        return this.controller;
    }

    @Override
    public PlayerPacketHandler getPlayerPacketHandler() {
        return playerPacketHandler;
    }

    @Override
    public PlayerTransmiter getPlayerTransmitter() {
        return this.playerTransmiter;
    }

    @Override
    public PlayerPacketCreator getPlayerPacketCreator() {
        return this.playerPacketCreator;
    }

    @Override
    public synchronized void aliveSignalReceive() {
        lastAliveSignalReceived = Instant.now().toEpochMilli();
        if (playerPacketCreator.getState() == playerPacketCreator.RESP_JOIN_STATE) {
            playerPacketCreator.setState(playerPacketCreator.WAITING_FOR_GAME_STATE);
        }
    }

    @Override
    public synchronized long getLastAliveSignalReceived() {
        return this.lastAliveSignalReceived;
    }

    public void stopTransmiter() {
        if (playerTransmiter.isAlive()) {
            this.playerTransmiter.interrupt();
        }
    }

}
