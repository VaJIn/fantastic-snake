package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.NetworkController;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class ConnectionPacketHandler implements PacketHandler {

    private NetworkController networkController;

    private static int BUFFER_START_POS = 16;

    public ConnectionPacketHandler(NetworkController networkController) {
        this.networkController = networkController;
    }

    @Override
    public void handlePacket(DatagramPacket packet) {

        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());

        buffer.position(BUFFER_START_POS);

        int type = buffer.getInt();

        if (type != PacketCreator.RESP_JOIN) {
            throw new IllegalArgumentException("Wrong packet type");
        }

        int resp = buffer.getInt();

        if (resp == 1) {

            int idPlayer = buffer.getInt();
            int tokenPlayer = buffer.getInt();

            networkController.setPlayerInfos(idPlayer, tokenPlayer);
            networkController.getApplication().getDirectConnectionScreen().acceptedConnection(packet.getAddress(), packet.getPort());
        } else {
            networkController.getApplication().getDirectConnectionScreen().refusedConnection(packet.getAddress(), packet.getPort());
        }
    }
}
