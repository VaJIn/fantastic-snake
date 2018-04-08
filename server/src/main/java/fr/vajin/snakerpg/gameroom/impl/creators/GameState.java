package fr.vajin.snakerpg.gameroom.impl.creators;

import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.Entity;
import fr.vajin.snakerpg.gameroom.Controller;
import fr.vajin.snakerpg.gameroom.PlayerHandler;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Iterator;

public class GameState implements PlayerPacketCreator.PlayerPacketCreatorState {

    private final Logger logger = LogManager.getLogger(GameState.class);

    private Controller controller;
    private PlayerPacketCreator parent;

    public GameState(PlayerPacketCreator creator) {

        this.parent = creator;

        PlayerHandler playerHandler = creator.getPlayerHandler();
        this.controller = playerHandler.getController();
    }

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {
        byte[] data;

        logger.debug("Building game packet");


        stream.writeInt(PlayerPacketCreator.GAME);

        GameEngine gameEngine = controller.getCurrentEngine();

        for (Entity entity : gameEngine.getEntityCollection()) {
            Iterator<? extends Entity.EntityTileInfo> it = entity.getEntityTilesInfosIterator();

            stream.writeInt(entity.getEntityId());
            while (it.hasNext()) {
                Entity.EntityTileInfo tileInfo = it.next();
                stream.writeInt(tileInfo.getId());
                stream.writeInt(tileInfo.getPosition().getX());
                stream.writeInt(tileInfo.getPosition().getY());
                byte[] resourceKeyBytes = tileInfo.getRessourceKey().getBytes();
                stream.writeInt(resourceKeyBytes.length);
                stream.write(resourceKeyBytes);
            }
            stream.writeInt(-1);
        }

        stream.writeInt(-1);

        data = stream.toByteArray();

        return new DatagramPacket(data, data.length);
    }

}
