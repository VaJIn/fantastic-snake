package fr.univangers.vajin.snakerpg.engine;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;


public class SimpleFoodRegistryImpl implements FoodRegistry {

    private List<Food> foodList;
    private Random gen = new Random();

    public SimpleFoodRegistryImpl() {
        this.gen = new Random();
        foodList = new ArrayList<>();
        foodList.add(new Food("Apple", "apple", 1));
        foodList.add(new Food("Beer", "beer", 3));
        foodList.add(new Food("Chicken", "chicken", 2));
    }

    @Override
    public Food getRandomFood() {
        int i = gen.nextInt(foodList.size());
        return foodList.get(i);

    }

    @Override
    public Food getFood(String key) {
        for (Food f : foodList) {
            if (f.getName().equals(key)) {
                return f;
            }
        }
        return null;
    }
}
