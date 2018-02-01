package fr.univangers.vajin.snakerpg.engine;

import fr.univangers.vajin.snakerpg.engine.utilities.Position;

import java.util.List;

/**
 * An entity than can act on its own, and therefore must be updated.
 */
public abstract class DynamicEntity extends Entity {

    /**
     * @param tick
     * @return true if the entity has changed
     */
    public abstract boolean computeTick(int tick); //To move

    public abstract List<Position> getNewPositions();
}
