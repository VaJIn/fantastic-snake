package fr.vajin.snakerpg.gameroom.impl;

import com.google.common.collect.Maps;
import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.utilities.Position;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;
import fr.vajin.snakerpg.gameroom.utilities.CustomByteArrayOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Iterator;
import java.util.Map;

public class PlayerPacketCreatorImpl implements PlayerPacketCreator {

    private GameEngine gameEngine;
    private Map<Integer, Entity> entities;
    private int idProtocol;
    private int lastIdReceived = 0;
    private int ackBitfield = 0;
    private int numSequence = 0;

    public PlayerPacketCreatorImpl(int idProtocol) {

        this.entities = Maps.newHashMap();
        this.idProtocol = idProtocol;
        this.gameEngine = null;
    }


    @Override
    public void setEngine(GameEngine gameEngine) {
        if (this.gameEngine != null) {
            for (Entity entity : entities.values()) {
                entity.removeObserver(this);
                this.gameEngine.removeGameEngineObserver(this);
            }
        }

        this.gameEngine = gameEngine;
        this.gameEngine.addGameEngineObserver(this);
        for (Entity entity : gameEngine.getEntities()) {
            System.out.println("[Set engine] registering entity " + entity.getEntityId());
            entity.registerObserver(this);
            this.entities.put(entity.getEntityId(), entity);
        }
    }

    private CustomByteArrayOutputStream getPacketStream() throws IOException {
        CustomByteArrayOutputStream stream = new CustomByteArrayOutputStream();

        numSequence++;

        stream.writeInt(idProtocol);

        stream.writeInt(numSequence);

        stream.writeInt(this.lastIdReceived);

        stream.writeInt(ackBitfield);

        return stream;
    }

    @Override
    public DatagramPacket getNextPacket() {

        byte[] data = new byte[0];

        try {

            CustomByteArrayOutputStream stream = this.getPacketStream();


            stream.write(GAME);

            System.out.println("[NEXT PACKET] Map size : " + entities.size());
            for (Entity entity : entities.values()) {
                System.out.println("[NEXT PACKET] Entity" + entity.getEntityId());
                Iterator<? extends Entity.EntityTileInfo> it = entity.getEntityTilesInfosIterator();

                stream.writeInt(entity.getEntityId());
                while (it.hasNext()) {
                    Entity.EntityTileInfo tileInfo = it.next();
                    System.out.println("[NEXT PACKET] Tile " + tileInfo.getId());
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Data.length : " + data.length);
        for (int i = 0; i < data.length; ++i) {
            System.out.println(i + " " + data[i]);
        }

        return new DatagramPacket(data, data.length);
    }

    @Override
    public void acknowledgePacket(int idReceived) {

        if (idReceived > this.lastIdReceived) {
            int predId = this.lastIdReceived;
            this.ackBitfield = this.ackBitfield >>> (idReceived - lastIdReceived);
            this.lastIdReceived = idReceived;
            this.acknowledgePacket(predId);
        } else if (idReceived < this.lastIdReceived && idReceived >= this.lastIdReceived - 32) {
            int mask = 0x80000000;
            mask = mask >>> (this.lastIdReceived - idReceived - 1);
            this.ackBitfield = this.ackBitfield | mask;
        }
    }


    //FROM INDIVIDUAL ENTITY

    @Override
    public void notifyDestroyed(Entity entity) {
        this.entities.remove(entity.getEntityId());
    }

    @Override
    public void notifyStateChange(Entity entity, int what) {

        //TODO

    }

    @Override
    public void notifyChangeAtPosition(Entity entity, Position position, int what) {
        //TODO
        switch (what) {
            case Entity.NEW_COVERED_POSITION:
                break;
            case Entity.NOT_COVER_POSITION_ANYMORE:
                break;
            case Entity.ONE_LESS_COVER_ON_POSITION:
                break;
        }
    }

    @Override
    public void notifySpriteChange(int id, Position newPosition, String newResource) {
//useless
    }

    //FROM GAME ENGINE

    @Override
    public void notifyNewEntity(Entity entity) {

        entities.put(entity.getEntityId(), entity);
        entity.registerObserver(this);

    }

    @Override
    public void notifyRemovedEntity(Entity entity) {

    }

    @Override
    public void notifyGameEnd() {

        //TODO
    }
}
