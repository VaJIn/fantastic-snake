package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;
import fr.univangers.vajin.screens.DirectConnectionScreen;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class ConnectionPacketHandler implements PacketHandler {

    DirectConnectionScreen directConnectionScreen;

    private static int BUFFER_START_POS = 16;

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
            directConnectionScreen.acceptedConnection(packet.getAddress(), packet.getPort());
        } else {
            directConnectionScreen.refusedConnection(packet.getAddress(), packet.getPort());
        }
    }
}
