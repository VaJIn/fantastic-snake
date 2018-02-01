package fr.univangers.vajin.engine;

public interface FoodRegistry {

    Food getRandomFood();

    Food getFood(String key);
}
