package fr.univangers.vajin.engine.entities.spawnables.food;

import fr.univangers.vajin.engine.entities.spawnables.SpawnableItem;


public class Food extends SpawnableItem{

    private final String name;
    private final int growthFactor;

    public Food(String name, int growthFactor, String ressourceKey, int probaWeight) {
        super(ressourceKey, probaWeight, name);
        this.growthFactor = growthFactor;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getGrowthFactor() {
        return growthFactor;
    }
}
