package fr.univangers.vajin.gamemodel.power;


import fr.univangers.vajin.gamemodel.mutableobject.MutableObject;

public interface Power {
    void enable(MutableObject target);

    void disable();
}
