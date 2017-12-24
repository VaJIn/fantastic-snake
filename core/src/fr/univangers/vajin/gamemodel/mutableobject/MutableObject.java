package fr.univangers.vajin.gamemodel.mutableobject;


import fr.univangers.vajin.gamemodel.Position;

import java.util.Iterator;

public interface MutableObject extends Iterable<MutableObjectAtom> {

    boolean coverPosition(Position position);

    boolean isBreakable();

    MutableObjectAtom getAtomAt(Position position);

    int getPrototypeId();

    Iterator<MutableObjectAtom> atomIterator();

    void registerObserver(MutableObjectObserver obs);

    void removeObserver(MutableObjectObserver obs);

    MutableObjectState getState();

    void setState(MutableObjectState state);
}
