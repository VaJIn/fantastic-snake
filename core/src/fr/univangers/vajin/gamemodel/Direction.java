package fr.univangers.vajin.gamemodel;

public enum Direction {

    NORTH, SOUTH, EAST, WEST;

    Direction rotateClockwise(){
        return rotate(true);
    }

    Direction rotateCounterClockwise(){
        return rotate(false);
    }

    Direction rotate(boolean clockwise) {
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
}
