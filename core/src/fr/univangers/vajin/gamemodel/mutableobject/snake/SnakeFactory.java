package fr.univangers.vajin.gamemodel.mutableobject.snake;

import fr.univangers.vrjlpv.snakerpg.gamemodel.Position;
import fr.univangers.vrjlpv.snakerpg.gamemodel.snakemodel.SnakeModel;

import java.util.List;

public interface SnakeFactory {
    /**
     * Create a Snake from a SnakeModel and a list of Position. The position must be adjacent positions, and will be the starting positions for the Snake.
     * @param model
     * @param startingPositions
     * @return a new Snake instance.
     */
    Snake createSnake(SnakeModel model, List<Position> startingPositions);
}
