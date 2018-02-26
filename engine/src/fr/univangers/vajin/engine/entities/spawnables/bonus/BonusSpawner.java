package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.DynamicEntity;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.Iterator;
import java.util.List;

public class BonusSpawner extends DynamicEntity {
    @Override
    public boolean computeTick(int tick) {
        return false;
    }

    @Override
    public List<Position> getNewPositions() {
        return null;
    }

    @Override
    public boolean coversPosition(Position pos) {
        return false;
    }

    @Override
    public void handleCollisionWith(Entity otherObject, Position collisionPosition, boolean isInitater) {

    }

    @Override
    public void inflictDamage(int damage) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String getGraphicRessourceKeyForPosition(Position pos) {
        return null;
    }

    @Override
    public Iterator<EntityTileInfo> getEntityTilesInfosIterator() {
        return null;
    }

    @Override
    public boolean isKiller() {
        return false;
    }
}
