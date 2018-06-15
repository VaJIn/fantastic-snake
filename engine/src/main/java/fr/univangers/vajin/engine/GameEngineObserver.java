package fr.univangers.vajin.engine;

import fr.univangers.vajin.engine.entities.Entity;

public interface GameEngineObserver {

    void notifyNewEntity(Entity entity);

    void notifyRemovedEntity(Entity entity);

    void notifyGameEnd();

}
