package fr.univangers.vajin.gamemodel;

public interface SnakeObserver extends EntityObserver {

    void notifyNewAtom(SnakeAtom atom);

    void notifyRemovedAtom(SnakeAtom atom);

    void notifyAtomUpdate(SnakeAtom atom);

}
