package fr.univangers.vajin.gamemodel.mutableobject.snake;


import fr.univangers.vajin.gamemodel.Position;
import fr.univangers.vajin.gamemodel.mutableobject.MovingMutableObject;
import fr.univangers.vajin.gamemodel.mutableobject.MutableObjectAtom;

public interface SnakeAtom extends MutableObjectAtom, MovingMutableObject {
    Position getPosition();

    SnakeAtom getForwardAtom();

    SnakeAtom getBackAtom();
}
