package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class PacketHandlerImpl implements PacketHandler {

    private PacketCreator packetCreator;

    public PacketHandlerImpl(PacketCreator packetCreator) {

        this.packetCreator = packetCreator;

    }


    @Override
    public void handlePacket(DatagramPacket packet) {


        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());


        int idProtocol = buffer.getInt();
        int num_sequence = buffer.getInt();
        int lastIdReceived = buffer.getInt();

        this.packetCreator.acknowledgePacket(lastIdReceived);

        int ackbitfield = buffer.getInt();

        int type = buffer.getInt();


        //TYPE = GAME;
        while (buffer.hasRemaining()) {

            int idEntity = buffer.getInt();
            if (idEntity == -1) {
                break;
            }
            int idTile;
            while ((idTile = buffer.getInt()) != -1) {
                int posX = buffer.getInt();
                int posY = buffer.getInt();
                int sizeRessourceKeyBytes = buffer.getInt();
                byte[] ressourceKey = new byte[sizeRessourceKeyBytes];

                buffer.get(ressourceKey);

            }

        }
    }
}
