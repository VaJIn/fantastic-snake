package fr.univangers.vajin.engine.entities.spawnables;


import fr.univangers.vajin.engine.entities.spawnables.bonus.Bonus;

/**
 * Interface of spawnable bonuses registery
 * The items can be accessed randomly or by their name
 */
public interface Registry {

    Bonus getRandom();
    Bonus getByName(String name);

}
