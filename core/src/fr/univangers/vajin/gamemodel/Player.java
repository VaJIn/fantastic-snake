package fr.univangers.vajin.gamemodel;


import fr.univangers.vajin.gamemodel.snakemodel.SnakeModel;

public interface Player {
    String getName();

    String getSessionToken();

    SnakeModel getSnakeModel();
}
