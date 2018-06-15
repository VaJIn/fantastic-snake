package fr.vajin.snakerpg.network.test;

import fr.univangers.vajin.engine.GameEngineObserver;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.EntityObserver;
import fr.univangers.vajin.engine.utilities.Position;

public class NotificationCounter implements GameEngineObserver, EntityObserver {

    private int newEntityNotificationCount = 0;
    private int removedEntityNotificationCount = 0;
    private int gameEndNotificationCount = 0;
    private int destroyedNotificationCount = 0;
    private int stateChangeNotificationCount = 0;
    private int changeAtPositionNotificationCount = 0;
    private int spriteChangeNotificationCount = 0;

    public void reset() {
        this.newEntityNotificationCount = 0;
        this.removedEntityNotificationCount = 0;
        this.gameEndNotificationCount = 0;
        this.destroyedNotificationCount = 0;
        this.stateChangeNotificationCount = 0;
        this.changeAtPositionNotificationCount = 0;
        this.spriteChangeNotificationCount = 0;
    }

    @Override
    public void notifyNewEntity(Entity entity) {
        newEntityNotificationCount++;
    }

    @Override
    public void notifyRemovedEntity(Entity entity) {
        removedEntityNotificationCount++;
    }

    @Override
    public void notifyGameEnd() {
        gameEndNotificationCount++;
    }

    @Override
    public void notifyDestroyed(Entity entity) {
        destroyedNotificationCount++;
    }

    @Override
    public void notifyStateChange(Entity entity, int what) {
        stateChangeNotificationCount++;
    }

    @Override
    public void notifyChangeAtPosition(Entity entity, Position position, int what) {
        changeAtPositionNotificationCount++;
    }

    @Override
    public void notifySpriteChange(int id, Position newPosition, String newResource) {
        spriteChangeNotificationCount++;
    }

    public int getNewEntityNotificationCount() {
        return newEntityNotificationCount;
    }

    public int getRemovedEntityNotificationCount() {
        return removedEntityNotificationCount;
    }

    public int getGameEndNotificationCount() {
        return gameEndNotificationCount;
    }

    public int getDestroyedNotificationCount() {
        return destroyedNotificationCount;
    }

    public int getStateChangeNotificationCount() {
        return stateChangeNotificationCount;
    }

    public int getChangeAtPositionNotificationCount() {
        return changeAtPositionNotificationCount;
    }

    public int getSpriteChangeNotificationCount() {
        return spriteChangeNotificationCount;
    }
}
