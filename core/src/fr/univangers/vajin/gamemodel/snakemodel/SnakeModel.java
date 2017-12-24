package fr.univangers.vajin.gamemodel.snakemodel;

import java.util.Collection;

public interface SnakeModel {
    int getBaseMaxLifePoint();

    int getBaseResistance();

    int getBaseLuckFactor();

    Collection<Equipement> getEquipements();
}
