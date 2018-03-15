package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.engine.utilities.Position;
import fr.univangers.vajin.network.DistantEngine;
import fr.univangers.vajin.network.DistantEntity;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class PacketHandlerImpl implements PacketHandler {

    private PacketCreator packetCreator;
    private DistantEngine distantEngine;

    public PacketHandlerImpl(PacketCreator packetCreator, DistantEngine distantEngine) {

        this.packetCreator = packetCreator;

        this.distantEngine = distantEngine;

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

        this.distantEngine.beginChange();

        while (buffer.hasRemaining()) {

            int idEntity = buffer.getInt();
            if (idEntity == -1) {
                break;
            }


            DistantEntity entity = this.distantEngine.getEntity(idEntity);

            entity.beginUpdate();

            int idTile;
            while ((idTile = buffer.getInt()) != -1) {
                int posX = buffer.getInt();
                int posY = buffer.getInt();
                int sizeRessourceKeyBytes = buffer.getInt();
                byte[] ressourceKey = new byte[sizeRessourceKeyBytes];

                buffer.get(ressourceKey);

                entity.setTile(idTile, new Position(posX,posY),String.valueOf(ressourceKey));

            }

            entity.endUpdate();

        }

        distantEngine.endChange();
    }
}
