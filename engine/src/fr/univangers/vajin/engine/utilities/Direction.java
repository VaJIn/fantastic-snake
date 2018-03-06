package fr.univangers.vajin.engine.utilities;

import static java.lang.Math.abs;

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

    public static Direction fromPosition(Position src, Position dest) {
        // System.out.println("src :" + src + " dest :" + dest);
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
        // System.out.println(" -> " + result);
        return result;
    }

    /**
     * Returns the direction between two adjacent positions
     * Undefined behaviour if the positions are not adjacent
     * If the positions are the same, returns null
     * @param pos
     * @param nextPos
     * @return
     */
    public static Direction getDirectionBetweenAdjPos(Position pos, Position nextPos){

        if (nextPos.getX()-pos.getX()>0){
            return EAST;
        }
        else if (nextPos.getX()-pos.getY()<0){
            return WEST;
        }
        else if (nextPos.getY()-pos.getY()>0){
            return SOUTH;
        }
        else if (nextPos.getY()-pos.getY()<0){
            return NORTH;
        }
        else{
            return null;
        }

    }

    @Override
    public String toString() {
        return super.toString();
    }

}
