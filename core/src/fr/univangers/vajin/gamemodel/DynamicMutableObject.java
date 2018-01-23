package fr.univangers.vajin.gamemodel;

import fr.univangers.vajin.gamemodel.utilities.Position;

public interface DynamicMutableObject extends MutableObject {

    void computeTick(int tick); //To move

    void handleCollisionWith(MutableObject otherObject, Position collisionPosition);


}
