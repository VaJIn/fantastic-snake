package fr.univangers.vajin.engine.entities.spawnables;


/**
 * Interface of spawnable items registery
 * The items can be accessed randomly or by their name
 */
public interface Registry {

    SpawnableItem getRandom();
    SpawnableItem getByName(String name);

}
