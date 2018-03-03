package fr.univangers.vajin.engine.entities.snake;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import fr.univangers.vajin.GameConstants;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.field.FieldUnit;
import fr.univangers.vajin.engine.utilities.Direction;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.*;

/**
 * A simple snake that move, grow and die.
 */
public class SimpleSnake extends Snake {

    private int size;

    //An atom A is referenced at the key K such as K.equals(A.getPosition())
    private Multimap<Position, SnakeAtom> atoms;

    //HEad of the snake
    private SnakeAtom head;
    //Tail of the snake
    private SnakeAtom tail;

    private Direction currentDirection;

    //FIFO list of the directions the user has entered
    private Deque<Direction> nextDirections;

    //Last tick the snake moved
    private int lastMoveTick;

    //Remaining size the snake has to grow. Can be negative if the snake must shrink
    private int leftToGrow;

    private List<Position> newPositions;


    public SimpleSnake() {
        super(100, 100, 0, 0, 10);

        this.atoms = MultimapBuilder.hashKeys().arrayListValues().build();


    }

    public void setInitialPosition(List<Position> startingPositions, Direction startingDirection) {
        this.currentDirection = startingDirection;
        this.nextDirections = new LinkedList<>();

        this.leftToGrow = 0;

        this.lastMoveTick = -1;

        Iterator<Position> it = startingPositions.iterator();

        this.tail = new SimpleSnakeAtom(it.next(), 0);
        SnakeAtom last = tail;
        atoms.put(last.getPosition(), last);
        while (it.hasNext()) {
            last = new SimpleSnakeAtom(it.next(), last);
            atoms.put(last.getPosition(), last);
        }
        this.head = last;
        this.size = atoms.size();

    }

    @Override
    public void moveForward() {

    }

    @Override
    public void moveBackward() {

    }

    @Override
    public void moveGrowing() {

    }

    @Override
    public void moveShrinking() {

    }

    private void removeTail() {

    }

    private void moveHeadForward() {

    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int changeSize(int howMuch) {
        return (leftToGrow = leftToGrow + howMuch);
    }

    @Override
    public int changeSpeed(int howMuch) {

        int delta;

        if (howMuch < 0) {
            if (getSpeed() + howMuch >= 1) {
                delta = howMuch;
                setSpeed(getSpeed() + howMuch);
            } else {
                delta = -getSpeed() + 1;
                setSpeed(1);
            }
        } else {
            setSpeed(getSpeed() + howMuch);
            delta = howMuch;
        }

        return delta;

    }

    @Override
    public void sendAction(int action) {

        switch (action) {

            case GO_SOUTH:
                this.nextDirections.addLast(Direction.SOUTH);
                break;
            case GO_WEST:
                this.nextDirections.addLast(Direction.WEST);
                break;
            case GO_NORTH:
                this.nextDirections.addLast(Direction.NORTH);
                break;
            case GO_EAST:
                this.nextDirections.addLast(Direction.EAST);
                break;

            default:
                throw new IllegalArgumentException("Unknown action " + action);
        }
    }

    @Override
    public Iterator<SnakeAtom> activatedAtomIterator() {
        return null;
    }

    @Override
    public boolean computeTick(int tick) {
        if (this.getLifePoint() <= 0) {
            //Snake is dead
            return false;
        }
        //znewPositions = new ArrayList<>();
        boolean destroyed = false;
        newPositions = new ArrayList<>();


        /*
            Speed in tile/s
            Tickrate = 32 tick/s;
            tickrate / speed -> tick/tile
         */
        if (tick - lastMoveTick > GameConstants.TICKRATE / this.getSpeed()) {
            //We move
            lastMoveTick = tick;


            //Updating the head position
            Position newHeadPosition = computeNextPosition();

            //check if we don't hit ourself if the snake is material
            if (!isImmaterial() && this.coversPosition(newHeadPosition)) {
                destroyed = true;
            }

            //Adding the new head position to the new positions
            this.newPositions.add(newHeadPosition);

            //Creating the new head atom
            SnakeAtom newHead = new SimpleSnakeAtom(newHeadPosition, head);

            //Notifying the sprite change of the former head
            notifySpriteChange(head.getId(), head.getPosition(), head.getGraphicKey());

            //Updating the reference to the snake atom head
            head = newHead;

            //Notifying the sprite change of the new head
            notifySpriteChange(head.getId(), head.getPosition(), head.getGraphicKey());

            //Adding the head to the list of the snake atoms
            atoms.put(head.getPosition(), head);

            //Notifying that the snake covers a new position
            notifyChangeAtPosition(newHeadPosition, Entity.NEW_COVERED_POSITION);

            //Check if new head position is valid on the board
            if (newHeadPosition.getX() < 0 || newHeadPosition.getX() >= this.getEngine().getField().getWidth() || newHeadPosition.getY() < 0 || newHeadPosition.getY() >= this.getEngine().getField().getHeight()) {
                this.destroy();
            } else {
                FieldUnit headFieldUnit = this.getEngine().getField().getFieldUnits(newHeadPosition);
                if (!headFieldUnit.isWalkable()) {
                    System.out.println("\033[31mField " + headFieldUnit + " ! You die\033[0m");
                    destroyed = true;
                }
            }


            if (leftToGrow > 0) {
                //If the snake must grow, recording that it has grown and updating the size
                leftToGrow--;
                size++;
            } else if (leftToGrow < 0) {


                leftToGrow++;
                size--;

                //Removing the tail of the snake
                //Remove tail
                tail.setActivated(false);
                notifySpriteChange(tail.getId(), tail.getPosition(), tail.getGraphicKey());
                notifyChangeAtPosition(tail.getPosition(), Entity.ONE_LESS_COVER_ON_POSITION);
                tail = tail.getAtomTowardsHead();
                notifySpriteChange(tail.getId(), tail.getPosition(), tail.getGraphicKey());

                //Removing the tail of the snake
                //Remove tail
                tail.setActivated(false);
                notifySpriteChange(tail.getId(), tail.getPosition(), tail.getGraphicKey());
                notifyChangeAtPosition(tail.getPosition(), Entity.ONE_LESS_COVER_ON_POSITION);
                tail = tail.getAtomTowardsHead();
                notifySpriteChange(tail.getId(), tail.getPosition(), tail.getGraphicKey());
            } else {
                //Removing the tail of the snake
                //Remove tail
                tail.setActivated(false);
                notifySpriteChange(tail.getId(), tail.getPosition(), tail.getGraphicKey());
                notifyChangeAtPosition(tail.getPosition(), Entity.ONE_LESS_COVER_ON_POSITION);
                tail = tail.getAtomTowardsHead();
                notifySpriteChange(tail.getId(), tail.getPosition(), tail.getGraphicKey());
            }


            if (destroyed) {
                this.destroy();
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Computes the next position for the snake
     *
     * @return
     */
    private Position computeNextPosition() {


        //Determining the next direction if the user entered a new direction
        //If the user entered several directions, only computing the first one for this tick
        if (!this.nextDirections.isEmpty()) {
            switch (this.nextDirections.poll()) {

                case EAST:
                    if (currentDirection != Direction.WEST) {
                        currentDirection = Direction.EAST;
                    }
                    break;
                case SOUTH:
                    if (currentDirection != Direction.NORTH) {
                        currentDirection = Direction.SOUTH;
                    }
                    break;
                case WEST:
                    if (currentDirection != Direction.EAST) {
                        currentDirection = Direction.WEST;
                    }
                    break;
                case NORTH:
                    if (currentDirection != Direction.SOUTH) {
                        currentDirection = Direction.NORTH;
                    }
                    break;
            }
        }


        Position newPosition = new Position(this.head.getPosition());
        newPosition.moveInDirection(currentDirection, 1);

        return newPosition;

    }

    @Override
    public List<Position> getNewPositions() {
        return newPositions;
    }

    @Override
    public boolean coversPosition(Position pos) {
        //For all atoms at position pos
        for (SnakeAtom sk : atoms.get(pos)) { //At worse the multimap return an empty collection, so no risk of NullPointerException
            if (sk.isActivated()) {
                //if an atom at this position is
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleCollisionWith(Entity otherObject, Position collisionPosition, boolean isInitater) {
        //EVOLUTION : encapsulate this method into a strategie pattern (power : snake dont die on contact with itself, as an example
        if (isInitater && otherObject.isKiller()) {
            this.destroy();
        } else {
            //Do nothing
        }
    }

    @Override
    public boolean isKiller() {
        return this.getLifePoint() > 0;
    }

    @Override
    public void inflictDamage(int damage) {
        //DAMAGE FORMULA : resistance decrease damage linearly, such as 0 resistance = no damage reduction, 100 resistance = full reduction
        this.setLifePoint(damage - (damage * this.getResistance()) / 100);
    }

    @Override
    public void destroy() {
        //Dis is death
        this.setLifePoint(0);
        this.notifyOfDestruction();
    }

    @Override
    public String getGraphicRessourceKeyForPosition(Position pos) {
        return null;
    }

    @Override
    public Iterator<Entity.EntityTileInfo> getEntityTilesInfosIterator() {
        return new EntityTileInfoInterator(head);
    }

    private class SimpleSnakeAtom extends SnakeAtom {

        private SimpleSnakeAtom(Position position, int id) {
            super(position, id);
        }

        private SimpleSnakeAtom(Position position, SnakeAtom towardsTail) {
            super(position, towardsTail.getId() + 1);
            this.atomTowardsTail = towardsTail;
            towardsTail.atomTowardsHead = this;
        }
    }

    private class EntityTileInfoInterator implements Iterator<Entity.EntityTileInfo> {

        SnakeAtom current;

        private EntityTileInfoInterator(SnakeAtom head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null && current.isActivated();
        }

        @Override
        public Entity.EntityTileInfo next() {
            if (current == null) {
                throw new NoSuchElementException("There is no next element !");
            }
            Entity.EntityTileInfo info = new Entity.EntityTileInfo() {
                final String ressourceKey = current.getGraphicKey();
                final Position pos = current.getPosition();
                final int id = current.getId();

                @Override
                public String getRessourceKey() {
                    return ressourceKey;
                }

                @Override
                public Position getPosition() {
                    return pos;
                }

                @Override
                public int getId() {
                    return id;
                }
            };

            current = current.getAtomTowardsTail();

            return info;
        }
    }
}
