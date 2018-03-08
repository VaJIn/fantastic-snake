package fr.vajin.snakerpg.gameroom.impl;

import com.google.common.collect.Maps;
import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.utilities.Position;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Iterator;
import java.util.Map;

public class PlayerPacketCreatorImpl implements PlayerPacketCreator {

    private GameEngine gameEngine;
    private Map<Integer, Entity> entities;
    private int idProtocol;
    private int lastIdReceived;
    private byte[] ackBitfield;

    public PlayerPacketCreatorImpl(int idProtocol) {

        this.entities = Maps.newHashMap();
        this.idProtocol = idProtocol;
    }


    @Override
    public void setEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.gameEngine.addGameEngineObserver(this);
        for (Entity entity : gameEngine.getEntities()) {
            entity.registerObserver(this);
            this.entities.put(entity.getEntityId(), entity);
        }
    }

    @Override
    public DatagramPacket getNextPacket() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        stream.write(idProtocol);

        stream.write(this.lastIdReceived);

        stream.write(0xffffffff);

        for (Entity entity : entities.values()) {

            Iterator<Entity.EntityTileInfo> it = entity.getEntityTilesInfosIterator();

            while (it.hasNext()) {
                Entity.EntityTileInfo tileInfo = it.next();
                try {
                    stream.write(tileInfo.getId());
                    stream.write(tileInfo.getPosition().getX());
                    stream.write(tileInfo.getPosition().getY());
                    byte[] resourceKeyBytes = tileInfo.getRessourceKey().getBytes();
                    stream.write(resourceKeyBytes.length);
                    stream.write(resourceKeyBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        byte[] data = stream.toByteArray();
        return new DatagramPacket(data, data.length);
    }

    @Override
    public void acknowledgePacket(int idLastReceived, byte[] ackBitField) {


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
