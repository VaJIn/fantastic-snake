package fr.univangers.vajin.gamemodel.mutableobject;

public interface MovingMutableObjectState extends MutableObjectState {
    void handleCollisionWith(MutableObject object);
}
