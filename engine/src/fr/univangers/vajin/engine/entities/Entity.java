package fr.univangers.vajin.engine.entities;

import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public abstract class Entity {

    private static int nextId = 0;

    private final int entityId;

    private List<EntityObserver> observers;
    private GameEngine engine;

    protected Entity() {
        this.entityId = nextId++;
        this.observers = new ArrayList<>();
    }

    public interface EntityTileInfo {

        String getResourceKey();

        Position getPosition();

        int getId();
    }

    public int getEntityId() {
        return entityId;
    }

    /**
     * @param pos the position to evaluate.
     * @return true if the object has an atom on the given position, else false.
     */
    public abstract boolean coversPosition(Position pos);

    public abstract void handleCollisionWith(Entity otherObject, Position collisionPosition, boolean isInitater);

    public abstract void inflictDamage(int damage);

    public abstract void destroy();

    public abstract String getGraphicResourceKeyForPosition(Position pos);

    public abstract Iterator<? extends EntityTileInfo> getEntityTilesInfosIterator();


    //Observable Pattern related methods

    public void registerObserver(EntityObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(EntityObserver observer) {
        this.observers.remove(observer);
    }

    public abstract boolean isKiller();

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    protected GameEngine getEngine() {
        return engine;
    }

    protected void notifyOfDestruction() {
        for (EntityObserver obs : observers) {
            obs.notifyDestroyed(this);
        }
    }

    protected void notifyOfStateChange(int what) {
        for (EntityObserver obs : observers) {
            obs.notifyStateChange(this, what);
        }
    }

    public static final int NEW_COVERED_POSITION = 0;
    public static final int NOT_COVER_POSITION_ANYMORE = 1;
    public static final int ONE_LESS_COVER_ON_POSITION = 2;

    protected void notifyChangeAtPosition(Position position, int what) {
        for (EntityObserver obs : observers) {
            obs.notifyChangeAtPosition(this, position, what);
        }
    }

    protected void notifySpriteChange(int id, Position newPosition, String newResource) {
        for (EntityObserver obs : observers) {
            obs.notifySpriteChange(id, newPosition, newResource);
        }
    }

    void dispose() {
        this.observers = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        return entityId == ((Entity) o).entityId;
    }

}