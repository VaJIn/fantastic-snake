package fr.univangers.vajin.gamemodel.mutableobject;

public interface MutableObjectObserver {

    void notifyDestruction(MutableObject o);

    void notifyPropertyChange(MutableObject o);

}
