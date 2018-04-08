package fr.univangers.vajin.network.impl;

import fr.univangers.vajin.engine.utilities.Position;
import fr.univangers.vajin.network.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

/**
 * Handle packet with the game type
 */
public class GamePacketHandler implements PacketHandler {

    private static final Logger logger = LogManager.getLogger(GamePacketHandler.class);

    private NetworkController networkController;

    public GamePacketHandler(NetworkController networkController) {
        this.networkController = networkController;
    }

    private static final int BUFFER_START_POS = 16;

    @Override
    public void handlePacket(DatagramPacket packet) {

        ByteBuffer buffer = ByteBuffer.wrap(packet.getData());

        final DistantEngine distantEngine = networkController.getDistantEngine();

        buffer.position(BUFFER_START_POS);

        int type = buffer.getInt();

        if (type != PacketCreator.GAME) {
            throw new IllegalArgumentException("Not a game packet !");
        }

        distantEngine.beginChange();

        boolean hasEntityLeft = true;
        while (buffer.hasRemaining() && hasEntityLeft) {
            hasEntityLeft = updateEntity(buffer, distantEngine);
        }

        distantEngine.endChange();
    }

    /**
     * Do the update of one Entity. Return false if there's no entity left
     *
     * @param buffer
     * @return
     */
    public boolean updateEntity(ByteBuffer buffer, DistantEngine distantEngine) {
        int idEntity = buffer.getInt();

        StringBuilder debugMessage;

        if (idEntity == -1) {
            logger.debug("Returning false");
            return false;
        } else {
            debugMessage = new StringBuilder("Updating Entity ").append(idEntity).append(System.lineSeparator());
        }

        DistantEntity entity = distantEngine.getEntity(idEntity);

        entity.beginUpdate();

        int idTile;
        while ((idTile = buffer.getInt()) != -1) {
            int posX = buffer.getInt();
            int posY = buffer.getInt();
            int sizeResourceKeyBytes = buffer.getInt();
            byte[] resourceKeyBytes = new byte[sizeResourceKeyBytes];

            buffer.get(resourceKeyBytes);

            String resourceKey = new String(resourceKeyBytes);

            debugMessage.append("\tTile ")
                    .append(idTile)
                    .append(" -> (")
                    .append(posX)
                    .append(", ")
                    .append(posY)
                    .append(") - ")
                    .append(resourceKey)
                    .append(System.lineSeparator());

            entity.setTile(idTile, new Position(posX, posY), resourceKey);
        }
        entity.endUpdate();

        logger.debug(debugMessage.toString());

        return true;
    }
}
