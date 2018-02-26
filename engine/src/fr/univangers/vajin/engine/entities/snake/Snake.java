package fr.univangers.vajin.engine.entities.snake;

import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.DynamicEntity;
import fr.univangers.vajin.engine.utilities.Direction;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.Iterator;
import java.util.List;

/**
 * Base class for a snake. Implement method that should not be changed in further implementation.
 * This class should be instanciated with an instance of SnakeFactory.
 */
public abstract class Snake extends DynamicEntity {

    /**
     * The maximum amount of life point a Snake can have.
     */
    private final int maxLifePoint;
    /**
     * The current amount of life point of the snake.
     */
    private int lifePoint;
    /**
     * The resistance of the snake. The more resistance a snake have, the more inflicted damage are reduced.
     */
    private int resistance;
    /**
     * A snake with a high luck factor might get bonus
     */
    private int luckFactor;
    /**
     * The speed of a snake.
     */
    private int speed;

    private GameEngine engine;

    protected Snake(int maxLifePoint, int lifePoint, int resistance, int luckFactor, int speed) {
        this.maxLifePoint = maxLifePoint;
        this.lifePoint = lifePoint;
        this.resistance = resistance;
        this.luckFactor = luckFactor;
        this.speed = speed;
    }


    public void setInitialPosition(List<Position> startingPositions, Direction startingDirection){


    }

    public int getMaxLifePoint() {
        return maxLifePoint;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public int getResistance() {
        return resistance;
    }

    public int getLuckFactor() {
        return luckFactor;
    }

    public int getSpeed() {
        return speed;
    }

    protected void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    protected void setResistance(int resistance) {
        this.resistance = resistance;
    }

    protected void setLuckFactor(int luckFactor) {
        this.luckFactor = luckFactor;
    }

    public abstract int getSize();

    public abstract int grow(int howMuch);

    public abstract int shrink(int howMuch);


    public static final int GO_NORTH = 0x0000;
    public static final int GO_EAST = 0x0001;
    public static final int GO_SOUTH = 0x0002;
    public static final int GO_WEST = 0x0003;

    public abstract void sendAction(int action);

    public abstract Iterator<SnakeAtom> activatedAtomIterator();

}
