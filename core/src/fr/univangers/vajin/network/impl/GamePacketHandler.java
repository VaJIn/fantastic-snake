package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.engine.utilities.Position;
import fr.univangers.vajin.network.DistantEngine;
import fr.univangers.vajin.network.DistantEntity;
import fr.univangers.vajin.network.PacketCreator;
import fr.univangers.vajin.network.PacketHandler;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

/**
 * Handle packet with the game type
 */
public class GamePacketHandler implements PacketHandler {

    private DistantEngine distantEngine;

    public GamePacketHandler() {
        this.distantEngine = new DistantEngine();
    }

    private static final int BUFFER_START_POS = 16;

    @Override
    public void handlePacket(DatagramPacket packet) {


        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());

        buffer.position(BUFFER_START_POS);

        int type = buffer.getInt();

        if (type != PacketCreator.GAME) {
            throw new IllegalArgumentException("Not a game packet !");
        }

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
                int sizeResourceKeyBytes = buffer.getInt();
                byte[] resourceKey = new byte[sizeResourceKeyBytes];

                buffer.get(resourceKey);

                entity.setTile(idTile, new Position(posX, posY), String.valueOf(resourceKey));

            }

            entity.endUpdate();

        }

        distantEngine.endChange();
    }

    public DistantEngine getDistantEngine() {
        return distantEngine;
    }
}
