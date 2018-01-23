package fr.univangers.vajin.gamemodel;

import fr.univangers.vajin.gamemodel.utilities.Position;

public abstract class SnakeAtom implements MutableObjectAtom {

    private final int id;

    public class Properties {
        private boolean breakable;
        private boolean visible;
        private boolean solid;


        public Properties(boolean breakable, boolean visible, boolean solid) {
            this.breakable = breakable;
            this.visible = visible;
            this.solid = solid;
        }
    }


    private Position position;

    private Properties properties;

    private boolean redifinesProperties;

    private boolean activated;

    protected SnakeAtom atomTowardsHead; //Towards the head of the snake, atomTowardsHead == null if this snakeAtom is the head
    protected SnakeAtom atomTowardsTail; // Toward the tails of the snake; atomTowardsTail == null if this snakeAtom is the tail

    protected SnakeAtom(Position position, int id) {
        this.position = position;
        this.id = id;
    }

    public boolean isActivated() {
        return activated;
    }

    protected void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean isBreakable() {
        if (redifinesProperties || atomTowardsHead == null) {
            return properties.breakable;
        }
        return atomTowardsHead.isBreakable();
    }

    @Override
    public boolean isVisible() {
        if (redifinesProperties || atomTowardsHead == null) {
            return properties.visible;
        }
        return atomTowardsHead.isVisible();
    }

    @Override
    public boolean isSolid() {
        if (redifinesProperties || atomTowardsHead == null) {
            return properties.solid;
        }
        return atomTowardsHead.isSolid();
    }


    public Position getPosition() {
        return position;
    }

    protected void redefinesProperties(Properties newProperties) {
        properties = newProperties;
        redifinesProperties = true;
    }

    protected void setDefaultProperties() {
        properties = null;
        redifinesProperties = false;
    }

    public SnakeAtom getAtomTowardsHead() {
        return atomTowardsHead;
    }

    public SnakeAtom getAtomTowardsTail() {
        return atomTowardsTail;
    }

    @Override
    public int getId() {
        return id;
    }
}
