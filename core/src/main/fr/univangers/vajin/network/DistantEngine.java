package fr.univangers.vajin.network;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.GameEngineObserver;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.engine.entities.spawnables.bonus.BonusTarget;
import fr.univangers.vajin.engine.entities.spawnables.bonus.TimedCommand;
import fr.univangers.vajin.engine.field.Field;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.*;

public class DistantEngine implements GameEngine {

    Map<Integer, DistantEntity> distantEntityMap;

    Collection<GameEngineObserver> observers;

    Collection<DistantEntity> leftToUpdate;
    private boolean updating;

    public DistantEngine() {
        this.distantEntityMap = Maps.newHashMap();
        this.observers = new ArrayList<>();
        this.updating = false;
        this.leftToUpdate = new TreeSet<>(Comparator.comparing(DistantEntity::getDistantId));
    }


    @Override
    public Field getField() {
        return null;
    }

    @Override
    public void computeTick() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendInput(int player, int input) {

    }

    @Override
    public void addGameEngineObserver(GameEngineObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeGameEngineObserver(GameEngineObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void addMovementTimedCommand(TimedCommand timedCommand) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addBonusTimedCommand(TimedCommand timedCommand) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void launchTimeMachine() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getCurrentTick() {
        return 0;
    }

    @Override
    public int getPlayerScore(int player) {
        return 0;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public boolean doesAnEntityCoverPosition(Position position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Snake> getAffectedSnakes(Snake taker, BonusTarget target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Entity> getEntityCollection() {
        return ImmutableList.copyOf(distantEntityMap.values());
    }

    public void beginChange() {
        this.leftToUpdate = new TreeSet<>(Comparator.comparingInt(DistantEntity::getDistantId));
        leftToUpdate.addAll(distantEntityMap.values());
        this.updating = true;
    }

    public void endChange() {
        for (DistantEntity entity : leftToUpdate) {
            distantEntityMap.remove(entity.getDistantId());
            entity.destroy();
            notifyOfRemovedEntity(entity);
        }

        this.updating = false;
    }

    public DistantEntity getEntity(int distantId) {
        DistantEntity entity = distantEntityMap.get(distantId);

        if (updating) {
            if (entity == null) {
                entity = new DistantEntity(distantId);
                distantEntityMap.put(distantId, entity);
                notifyOfNewEntity(entity);
            } else {
                leftToUpdate.remove(entity);
            }
        }

        return entity;
    }

    private void notifyOfNewEntity(Entity entity) {
        for (GameEngineObserver observer : observers) {
            observer.notifyNewEntity(entity);
        }
    }

    private void notifyOfRemovedEntity(Entity entity) {
        for (GameEngineObserver observer : observers) {
            observer.notifyRemovedEntity(entity);
        }
    }

}
