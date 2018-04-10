package fr.univangers.vajin.engine.utilities;

public enum Direction {

    NORTH,
    SOUTH,
    EAST,
    WEST;

    Direction rotateClockwise() {
        return rotate(true);
    }

    Direction rotateCounterClockwise() {
        return rotate(false);
    }

    public Direction rotate(boolean clockwise) {
        switch (this) {
            case EAST:
                return clockwise ? SOUTH : NORTH;
            case WEST:
                return clockwise ? NORTH : SOUTH;
            case NORTH:
                return clockwise ? EAST : WEST;
            case SOUTH:
                return clockwise ? WEST : EAST;
        }
        return null; //This should never happen.
    }

    /**
     * Returns the direction between two adjacent positions
     * Undefined behaviour if the positions are not adjacent
     * If the positions are the same, returns null
     */
    public static Direction fromPosition(Position src, Position dest) {
        Direction result = null;
        if (src.getX() == dest.getX()) {
            if (src.getY() == dest.getY()) {
                result = null;
            } else {
                result = src.getY() - dest.getY() < 0 ? NORTH : SOUTH;

            }
        } else if (src.getY() == dest.getY()) {
            result = src.getX() - dest.getX() < 0 ? EAST : WEST;
        }
        return result;
    }



    @Override
    public String toString() {
        return super.toString();
    }

}
