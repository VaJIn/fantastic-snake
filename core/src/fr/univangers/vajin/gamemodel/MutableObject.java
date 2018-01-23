package fr.univangers.vajin.gamemodel;

import fr.univangers.vajin.gamemodel.utilities.Position;

public interface MutableObject {

    boolean coverPosition(Position pos);

    MutableObjectAtom getAtomAt(Position pos);

    void inflictDamage(int damage);

    void destroy();

    void registerObserver(MutableObjectObserver observer);

    void removeObserver(MutableObjectObserver observer);


}
