package fr.univangers.vajin.gamemodel.mutableobject;

public interface MutableObjectAtom extends MutableObject {
    MutableObject getParent();

    boolean isVisible();

    boolean isSolid();
}
