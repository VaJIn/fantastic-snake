package fr.univangers.vajin.gamemodel.snakemodel;

import java.util.Map;

public interface Equipement {
    int getStat(String key);

    Map<String, Integer> getStats();

    String getCategory();
}
