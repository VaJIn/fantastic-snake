package fr.univangers.vajin.engine;

import fr.univangers.vajin.engine.utilities.Direction;
import fr.univangers.vajin.engine.utilities.Position;

public abstract class SnakeAtom {

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

    private boolean redefinesProperties;

    private boolean activated;

    protected SnakeAtom atomTowardsHead; //Towards the head of the snake, atomTowardsHead == null if this snakeAtom is the head
    protected SnakeAtom atomTowardsTail; // Toward the tails of the snake; atomTowardsTail == null if this snakeAtom is the tail

    protected SnakeAtom(Position position, int id) {
        this.position = position;
        this.id = id;
        this.activated = true;
    }

    public boolean isActivated() {
        return activated;
    }

    protected void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isBreakable() {
        if (redefinesProperties || atomTowardsHead == null) {
            return properties.breakable;
        }
        return atomTowardsHead.isBreakable();
    }

    public boolean isVisible() {
        if (redefinesProperties || atomTowardsHead == null) {
            return properties.visible;
        }
        return atomTowardsHead.isVisible();
    }

    public boolean isSolid() {
        if (redefinesProperties || atomTowardsHead == null) {
            return properties.solid;
        }
        return atomTowardsHead.isSolid();
    }


    public Position getPosition() {
        return position;
    }

    protected void redefinesProperties(Properties newProperties) {
        properties = newProperties;
        redefinesProperties = true;
    }

    protected void setDefaultProperties() {
        properties = null;
        redefinesProperties = false;
    }

    public SnakeAtom getAtomTowardsHead() {
        return atomTowardsHead;
    }

    public SnakeAtom getAtomTowardsTail() {
        return atomTowardsTail;
    }

    public int getId() {
        return id;
    }

    public String getGraphicKey() {
        if (!this.isActivated()) {
            return "";
        }
        if (this.getAtomTowardsHead() == null) {
            Direction fromDir = Direction.fromPosition(this.getAtomTowardsTail().getPosition(), this.getPosition());
            return "head_" + fromDir.toString().toLowerCase();
        } else if (this.getAtomTowardsTail() == null || !this.getAtomTowardsTail().isActivated()) {
            Direction toDir = Direction.fromPosition(this.getPosition(), this.getAtomTowardsHead().getPosition());
            return "tail_" + toDir.toString().toLowerCase();
        } else {
            Direction fromDir = Direction.fromPosition(this.getPosition(), this.getAtomTowardsTail().getPosition());
            Direction toDir = Direction.fromPosition(this.getPosition(), this.getAtomTowardsHead().getPosition());
            return fromDir.toString().toLowerCase() + "_" + toDir.toString().toLowerCase();
        }
    }

}
