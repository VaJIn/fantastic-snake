package fr.univangers.vajin.snakerpg.engine;

public interface SnakeObserver extends EntityObserver {

    void notifyNewAtom(SnakeAtom atom);

    void notifyRemovedAtom(SnakeAtom atom);

    void notifyAtomUpdate(SnakeAtom atom);

}
