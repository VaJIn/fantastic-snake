package fr.univangers.vajin.gamemodel;

import java.util.List;

public interface GameEngine {


    List<MutableObject> getMutable();

    void computeTick();

}
