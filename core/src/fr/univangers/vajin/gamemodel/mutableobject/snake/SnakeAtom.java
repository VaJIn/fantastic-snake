package fr.univangers.vajin.gamemodel.mutableobject.snake;

import fr.univangers.vrjlpv.snakerpg.gamemodel.Position;
import fr.univangers.vrjlpv.snakerpg.gamemodel.mutableobject.MovingMutableObject;
import fr.univangers.vrjlpv.snakerpg.gamemodel.mutableobject.MutableObjectAtom;

public interface SnakeAtom extends MutableObjectAtom, MovingMutableObject {
    Position getPosition();

    SnakeAtom getForwardAtom();

    SnakeAtom getBackAtom();
}
