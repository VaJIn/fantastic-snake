package fr.univangers.vajin.gamemodel;

public interface MutableObjectObserver {

    void notifyDestroyed(MutableObject obj);

    void notifyStateChange(MutableObject obj, int what);

    void notifyPositionChange(MutableObject obj);
}
