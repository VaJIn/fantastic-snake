package fr.univangers.vajin.snakerpg.engine;

public interface GameEngineObserver {

    void notifyNewEntity(Entity entity);

    void notifyRemovedEntity(Entity entity);

    void notifyGameEnd();

}
