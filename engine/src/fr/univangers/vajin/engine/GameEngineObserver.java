package fr.univangers.vajin.engine;

public interface GameEngineObserver {

    void notifyNewEntity(Entity entity);

    void notifyRemovedEntity(Entity entity);

    void notifyGameEnd();

}
