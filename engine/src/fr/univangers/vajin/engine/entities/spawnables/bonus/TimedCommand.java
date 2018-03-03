package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.snake.Snake;

/**
 * Interface of the class whose role is to apply and cancel bonus effects
 */
public interface TimedCommand extends Comparable{

    /**
     * Interface of the lambda function that must be passed as an argument that affects a snake
     */
    interface BonusTimedLambda {
        void op(Snake snake);
    }

    void apply();

    void cancel();

    int getTick();


}
