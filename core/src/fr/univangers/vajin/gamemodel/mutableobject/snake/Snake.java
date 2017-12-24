package fr.univangers.vajin.gamemodel.mutableobject.snake;


import fr.univangers.vajin.gamemodel.SnakeState;
import fr.univangers.vajin.gamemodel.mutableobject.MovingMutableObject;

import java.util.Iterator;

public interface Snake extends MovingMutableObject {

    boolean canGoThroughSolid();

    boolean canFlyInVoid();

    boolean isVisible();

    int getMaxLifePoint();

    int getLuckFactor();

    int getResistance();

    int getLifePoint();

    SnakeAtom getHead();

    void addGrowth();

    void reduceGrowth();

    Iterator<SnakeAtom> snakeAtomIterator();

    void setState(SnakeState state);

    void applyPropertyToAllAtom();

}
