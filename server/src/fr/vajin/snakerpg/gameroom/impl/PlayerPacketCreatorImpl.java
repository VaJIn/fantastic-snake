package fr.vajin.snakerpg.gameroom.impl;

import com.google.common.collect.Maps;
import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.utilities.Position;
import fr.vajin.snakerpg.gameroom.PlayerPacketCreator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;

public class PlayerPacketCreatorImpl implements PlayerPacketCreator {


    private static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    private GameEngine gameEngine;
    private Map<Integer, Entity> entities;
    private int idProtocol;
    private int lastIdReceived;
    private byte[] ackBitfield;

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

    @Override
    public DatagramPacket getNextPacket() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            stream.write(intToByteArray(idProtocol));

            stream.write(intToByteArray(this.lastIdReceived));

            stream.write(intToByteArray(0xFFFFFFFF));

            System.out.println("[NEXT PACKET] Map size : " + entities.size());

            for (Entity entity : entities.values()) {
                System.out.println("[NEXT PACKET] Entity" + entity.getEntityId());
                Iterator<Entity.EntityTileInfo> it = entity.getEntityTilesInfosIterator();

                stream.write(intToByteArray(entity.getEntityId()));
                while (it.hasNext()) {
                    Entity.EntityTileInfo tileInfo = it.next();
                    System.out.println("[NEXT PACKET] Tile " + tileInfo.getId());
                    stream.write(intToByteArray(tileInfo.getId()));
                    stream.write(intToByteArray(tileInfo.getPosition().getX()));
                    stream.write(intToByteArray(tileInfo.getPosition().getY()));
                    byte[] resourceKeyBytes = tileInfo.getRessourceKey().getBytes();
                    stream.write(intToByteArray(resourceKeyBytes.length));
                    stream.write(resourceKeyBytes);
                }
                stream.write(intToByteArray(-1));
            }

            stream.write(intToByteArray(-1));

        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = stream.toByteArray();

        System.out.println("Data.length : " + data.length);
        for (int i = 0; i < data.length; ++i) {
            System.out.println(i + " " + data[i]);
        }

//*
        System.out.println("==================================================================");
        ByteBuffer buffer = ByteBuffer.wrap(data);
        int idProtocol = buffer.getInt();
        System.out.format("idProtocol : 0x%08X", idProtocol);
        System.out.println();

        int lastIdReceived = buffer.getInt();

        System.out.println("lastIdReceived : " + lastIdReceived);

        int ackbitfield = buffer.getInt();

        System.out.println("ackbitfield : " + ackbitfield);

        while (buffer.hasRemaining()) {
            int idEntity = buffer.getInt();
            System.out.println("idEntity : " + idEntity);
            if (idEntity == -1) {
                break;
            }
            int idTile;
            while ((idTile = buffer.getInt()) != -1) {
                System.out.println("idTile : " + idTile);
                int posX = buffer.getInt();
                System.out.println("posXTile : " + posX);
                int posY = buffer.getInt();
                System.out.println("posYTile : " + posY);
                int sizeRessourceKeyBytes = buffer.getInt();
                System.out.println("sizeRessourceKeyBytes : " + sizeRessourceKeyBytes);
                byte[] ressourceKey = new byte[sizeRessourceKeyBytes];
                buffer.get(ressourceKey);
                System.out.println("ressourceKey : " + new String(ressourceKey));
            }
        }

//*/
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
