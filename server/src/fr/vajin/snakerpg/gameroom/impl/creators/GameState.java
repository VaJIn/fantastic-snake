package fr.vajin.snakerpg.gameroom.impl.creators;

import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.Entity;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.utilities.CustomByteArrayOutputStream;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Iterator;

public class GameState implements PlayerPacketCreator.PlayerPacketCreatorState {

    private GameEngine gameEngine;

    public GameState() {

    }

    @Override
    public DatagramPacket getNextPacket(CustomByteArrayOutputStream stream) throws IOException {
        byte[] data;


        stream.writeInt(PlayerPacketCreator.GAME);

        for (Entity entity : this.gameEngine.getEntities()) {
            Iterator<? extends Entity.EntityTileInfo> it = entity.getEntityTilesInfosIterator();

            stream.writeInt(entity.getEntityId());
            while (it.hasNext()) {
                Entity.EntityTileInfo tileInfo = it.next();
                stream.writeInt(tileInfo.getId());
                stream.writeInt(tileInfo.getPosition().getX());
                stream.writeInt(tileInfo.getPosition().getY());
                byte[] resourceKeyBytes = tileInfo.getResourceKey().getBytes();
                stream.writeInt(resourceKeyBytes.length);
                stream.write(resourceKeyBytes);
            }
            stream.writeInt(-1);
        }

        stream.writeInt(-1);

        data = stream.toByteArray();


        return new DatagramPacket(data, data.length);
    }

    public void setEngine(GameEngine gameEngine) {
        /*if (this.gameEngine != null) {
            for (Entity entity : entities.values()) {
                entity.removeObserver(this);
                this.gameEngine.removeGameEngineObserver(this);
            }
        }*/

        this.gameEngine = gameEngine;
        /*this.gameEngine.addGameEngineObserver(this);
        for (Entity entity : gameEngine.getEntities()) {
            System.out.println("[Set engine] registering entity " + entity.getEntityId());
            entity.registerObserver(this);
            this.entities.put(entity.getEntityId(), entity);
        }*/
    }

}