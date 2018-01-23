package fr.univangers.vajin.gamemodel;

public interface SnakeObserver extends MutableObjectObserver {

    void notifyNewAtom(SnakeAtom atom);

    void notifyRemovedAtom(SnakeAtom atom);

    void notifyAtomUpdate(SnakeAtom atom);

}
