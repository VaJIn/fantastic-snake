package fr.univangers.vajin.network;

import com.google.common.collect.Maps;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.Iterator;
import java.util.Map;

public class DistantEntity extends Entity {

    private final int distantId;
    private Map<Integer, DistantEntityTileInfo> oldEntityTileInfoMap;
    private Map<Integer, DistantEntityTileInfo> entityTileInfoMap;
    private boolean updating;

    public DistantEntity(int distantId) {
        this.distantId = distantId;
        this.entityTileInfoMap = Maps.newHashMap();
        this.updating = false;
    }

    @Override
    public boolean coversPosition(Position pos) {
        return false; //we don't care
    }

    @Override
    public void handleCollisionWith(Entity otherObject, Position collisionPosition, boolean isInitater) {
        //Nothing
    }

    @Override
    public void inflictDamage(int damage) {
        //Nothing
    }

    @Override
    public void destroy() {
        this.notifyOfDestruction();
    }

    @Override
    public boolean isKiller() {
        return false;
    }

    @Override
    public String getGraphicRessourceKeyForPosition(Position pos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<? extends EntityTileInfo> getEntityTilesInfosIterator() {
        return entityTileInfoMap.values().iterator();
    }

    public int getDistantId() {
        return distantId;
    }

    private void addTile(int id, Position pos, String resource) {
        DistantEntityTileInfo tileInfo = new DistantEntityTileInfo(id, pos, resource);
        entityTileInfoMap.put(id, tileInfo);

        this.notifyChangeAtPosition(pos, Entity.NEW_COVERED_POSITION);
        this.notifySpriteChange(id, pos, resource);

    }

    private void updateTile(int id, Position pos, String resource) {

        DistantEntityTileInfo oldTileInfo = (DistantEntityTileInfo) oldEntityTileInfoMap.get(id);

        if (!oldTileInfo.getResourceKey().equals(resource)) {
            notifySpriteChange(id, pos, resource);
        }

        if (!oldTileInfo.getPosition().equals(pos)) {
            Position oldPos = oldTileInfo.getPosition();

            notifySpriteChange(id, pos, resource);
            notifySpriteChange(id, oldPos, "");
            notifyChangeAtPosition(oldPos, NOT_COVER_POSITION_ANYMORE);
            notifyChangeAtPosition(pos, NEW_COVERED_POSITION);
        }
    }

    public void setTile(int id, Position pos, String resource) {
        if (!this.updating) {
            throw new UnsupportedOperationException("Setting tile without calling beginUpdate() before !");
        }

        if (oldEntityTileInfoMap.containsKey(id)) {
            updateTile(id, pos, resource);
        } else {
            addTile(id, pos, resource);
        }
    }

    public void beginUpdate() {
        this.oldEntityTileInfoMap = this.entityTileInfoMap;
        this.entityTileInfoMap = Maps.newHashMap();
        this.updating = true;
    }

    public void endUpdate() {
        this.oldEntityTileInfoMap = null;
        this.updating = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DistantEntity that = (DistantEntity) o;

        return distantId == that.distantId;
    }

    @Override
    public int hashCode() {
        return distantId;
    }

    public class DistantEntityTileInfo implements EntityTileInfo {

        int id;
        Position position;
        String resourceKey;

        DistantEntityTileInfo(int id, Position position, String resourceKey) {
            this.id = id;
            this.position = position;
            this.resourceKey = resourceKey;
        }

        @Override
        public String getResourceKey() {
            return resourceKey;
        }

        public void setResourceKey(String resourceKey) {
            this.resourceKey = resourceKey;
        }

        @Override
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }
    }
}
