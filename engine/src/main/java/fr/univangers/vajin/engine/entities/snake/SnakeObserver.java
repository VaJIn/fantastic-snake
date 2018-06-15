package fr.univangers.vajin.engine.entities.snake;

import fr.univangers.vajin.engine.entities.EntityObserver;

public interface SnakeObserver extends EntityObserver {

    void notifyNewAtom(SnakeAtom atom);

    void notifyRemovedAtom(SnakeAtom atom);

    void notifyAtomUpdate(SnakeAtom atom);

}
