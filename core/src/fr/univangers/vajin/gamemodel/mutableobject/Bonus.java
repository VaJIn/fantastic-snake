package fr.univangers.vajin.gamemodel.mutableobject;


import fr.univangers.vajin.gamemodel.power.Power;

import java.util.List;

public interface Bonus extends MutableObjectAtom {

    static final int ALL_SNAKE = 0;
    static final int TAKER_ONLY = 1;
    static final int ALL_SNAKES_BUT_TAKER = 2;

    int getApplicationFlag();

    List<Power> getPowers();

    boolean isPositive();

    boolean isActivationImmediate();
}
