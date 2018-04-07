package fr.univangers.vajin.engine;

import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.engine.entities.spawnables.bonus.TimedCommand;
import fr.univangers.vajin.engine.entities.spawnables.bonus.BonusTarget;
import fr.univangers.vajin.engine.field.Field;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.Collection;

public interface GameEngine {

    Collection<Entity> getEntityCollection();

    Field getField();

    void computeTick();

    void sendInput(int player, int input);

    void addGameEngineObserver(GameEngineObserver observer);

    void removeGameEngineObserver(GameEngineObserver observer);

    void addMovementTimedCommand(TimedCommand timedCommand);

    void addBonusTimedCommand(TimedCommand timedCommand);

    void launchTimeMachine();

    int getCurrentTick();

    int getPlayerScore(int player);

    boolean isGameOver();

    boolean doesAnEntityCoverPosition(Position position);

    /**
     * Returns the list of the affected snakes by a bonus depending on the BonusTarget and by the Snake which triggered the bonus
     * @param taker Snake that triggered the bonus
     * @param target BonusTarget of the triggered bonus
     * @return
     */
    Collection<Snake> getAffectedSnakes(Snake taker, BonusTarget target);
}