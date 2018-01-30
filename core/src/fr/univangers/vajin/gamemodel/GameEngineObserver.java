package fr.univangers.vajin.gamemodel;

public interface GameEngineObserver {

    void notifyNewEntity(Entity entity);

    void notifyRemovedEntity(Entity entity);

    void notifyGameEnd();

}
