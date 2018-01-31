package fr.univangers.vajin.gamemodel;

public interface FoodRegistry {

    Food getRandomFood();

    Food getFood(String key);
}
