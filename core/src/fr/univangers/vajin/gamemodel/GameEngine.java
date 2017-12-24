package fr.univangers.vajin.gamemodel;

import fr.univangers.vajin.gamemodel.field.Field;
import fr.univangers.vajin.gamemodel.mutableobject.MutableObject;

import java.util.List;

public interface GameEngine {
    List<MutableObject> getMutables();

    Field getField();

    /**
     * Compute the next tick of the game.
     * To compute the next tick of the game, the engine must :
     *  -> Call the method move() on all its MovingMutableObject
     *  -> Check for collision on the new state of the game
     *  ->
     */
    void computeNextTick();

    int getTickRate();

    int setTickRate();
}
