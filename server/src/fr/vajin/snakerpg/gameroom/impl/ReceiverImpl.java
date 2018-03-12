package fr.vajin.snakerpg.gameroom.impl;

import com.google.common.collect.Maps;
import fr.vajin.snakerpg.gameroom.Controller;
import fr.vajin.snakerpg.gameroom.NewConnectionHandler;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.Receiver;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Map;

public class ReceiverImpl implements Receiver {

    private final int idProtocol;
    private Controller controller;
    private Map<Integer, PlayerHandler> playerHandlerMap;
    private NewConnectionHandler newConnectionHandler;

    public ReceiverImpl(int idProtocol, Controller controller, NewConnectionHandler newConnectionHandler) {
        this.idProtocol = idProtocol;
        this.playerHandlerMap = Maps.newConcurrentMap();
        this.newConnectionHandler = newConnectionHandler;
        this.controller = controller;
    }

    @Override
    public void receivePacket(DatagramPacket packet) {
        byte[] data = packet.getData();


        ByteBuffer buffer = ByteBuffer.wrap(data);


        int idProtocol = buffer.getInt();
        if (idProtocol == this.idProtocol) {
            int playerId = buffer.getInt();

            if (playerHandlerMap.containsKey(playerId)) {
                playerHandlerMap.get(playerId).getPlayerPacketHandler().handleDatagramPacket(packet);
            } else {
                newConnectionHandler.handleDatagramPacket(packet);
            }
        }

    }

    @Override
    public void setNewConnectionHandler(NewConnectionHandler newConnectionHandler) {
        this.newConnectionHandler = newConnectionHandler;
    }

    @Override
    public void addPlayerHandler(PlayerHandler handler) {
        playerHandlerMap.put(handler.getUserId(), handler);
    }

    @Override
    public void removePlayerHandler(PlayerHandler handler) {
        playerHandlerMap.remove(handler.getUserId());
    }
}
