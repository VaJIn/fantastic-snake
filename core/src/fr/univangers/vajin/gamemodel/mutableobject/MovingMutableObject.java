package fr.univangers.vajin.gamemodel.mutableobject;


import fr.univangers.vajin.gamemodel.Direction;

public interface MovingMutableObject extends MutableObject {
    void move();

    void moveBack();

    boolean canBreakStuff();

    Direction getDirection();

}
