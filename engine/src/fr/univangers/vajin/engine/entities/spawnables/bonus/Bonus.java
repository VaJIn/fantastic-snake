package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.spawnables.SpawnableItem;

public class Bonus extends SpawnableItem{

    public Bonus(BonusType type, BonusTarget target, String resourceKey, int probaWeight, String name){
        super(resourceKey, probaWeight, name);
    }



}
