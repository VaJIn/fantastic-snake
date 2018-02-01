package fr.univangers.vajin.engine;

import fr.univangers.vajin.engine.utilities.Position;

public interface EntityObserver {

    int GRAPHIC_CHANGE_MASK = 0xF000;
    int NOW_COVER_POSITON = 0x0001;
    int POSTION_NOT_COVERED_ANYMORE = 0x0002;

    void notifyDestroyed(Entity entity);

    void notifyStateChange(Entity entity, int what);

    /**
     * Notify of a change specific to one of the position covered of an entity (ie, part of a snake is added)
     *
     * @param entity   the entity that emit the signal
     * @param position the position affected by the change
     * @param what     the type of change that occured
     */
    void notifyChangeAtPosition(Entity entity, Position position, int what);

    void notifySpriteChange(int id, Position newPosition, String newResource);
}
