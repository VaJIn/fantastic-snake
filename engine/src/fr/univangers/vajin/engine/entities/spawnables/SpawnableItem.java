package fr.univangers.vajin.engine.entities.spawnables;

public class SpawnableItem {


    private String resourceKey;
    private int probaWeight;
    private String name;

    public SpawnableItem(String resourceKey, int probaWeight, String name){
        this.resourceKey = resourceKey;
        this.probaWeight = probaWeight;
        this.name = name;
    }

    public int getProbaWeight() {
        return probaWeight;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public String getName() {
        return name;
    }
}
