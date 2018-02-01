package fr.univangers.vajin.snakerpg.engine;

public class Food implements Cloneable {

    private final String name;
    private final String ressourceKey;
    private final int growthFactor;

    public Food(String name, String ressourceKey, int growthFactor) {
        this.name = name;
        this.ressourceKey = ressourceKey;
        this.growthFactor = growthFactor;
    }

    public String getRessourceKey() {
        return ressourceKey;
    }

    public int getGrowthFactor() {
        return growthFactor;
    }

    public String getName() {
        return name;
    }
}
