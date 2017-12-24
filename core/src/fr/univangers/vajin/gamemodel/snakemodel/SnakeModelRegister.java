package fr.univangers.vajin.gamemodel.snakemodel;

import java.util.Set;

public interface SnakeModelRegister {
    SnakeModel getSnakeModel(String key);

    Set<String> getKeySet();

    boolean addModel(String key, SnakeModel snakeModel);
}
