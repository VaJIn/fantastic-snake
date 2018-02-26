package fr.univangers.vajin.engine.entities.spawnables.food;

import fr.univangers.vajin.engine.entities.spawnables.RegistryImpl;

import java.util.List;

/**
 * Stores a list of food
 * The food can be accessed randomly depending on their probability weight, or can be accessed by their name
 */
public class FoodRegistryImpl extends RegistryImpl {


    public FoodRegistryImpl(List<Food> foodList) {
        super(foodList);
    }

    @Override
    public Food getRandom() {
        return ((Food) super.getRandom());
    }

    @Override
    public Food getByName(String name) {
        return ((Food) super.getByName(name));
    }
}
