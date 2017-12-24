package fr.univangers.vajin.gamemodel;

import fr.univangers.vrjlpv.snakerpg.gamemodel.snakemodel.SnakeModel;

public interface Player {
    String getName();

    String getSessionToken();

    SnakeModel getSnakeModel();
}
