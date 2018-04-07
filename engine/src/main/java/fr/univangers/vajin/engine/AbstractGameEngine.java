package fr.univangers.vajin.engine;

import fr.univangers.vajin.engine.entities.Entity;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractGameEngine implements GameEngine {

    Collection<GameEngineObserver> observerCollection;

    protected AbstractGameEngine() {
        this.observerCollection = new ArrayList<>();
    }

    @Override
    public void addGameEngineObserver(GameEngineObserver observer) {
        observerCollection.add(observer);
    }

    @Override
    public void removeGameEngineObserver(GameEngineObserver observer) {
        observerCollection.remove(observer);
    }

    protected void notifyOfNewEntity(Entity entity) {
        for (GameEngineObserver obs : observerCollection) {
            obs.notifyNewEntity(entity);
        }
    }

    protected void notifyOfRemovedEntity(Entity entity) {
        for (GameEngineObserver obs : observerCollection) {
            obs.notifyRemovedEntity(entity);
        }
    }

    protected void notifyOfGameEnd() {
        for (GameEngineObserver obs : observerCollection) {
            obs.notifyGameEnd();
        }
    }
}
