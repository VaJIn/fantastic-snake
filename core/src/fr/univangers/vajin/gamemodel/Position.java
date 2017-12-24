package fr.univangers.vajin.gamemodel;


/**
 * Une position, caractérisée par ses coordonnées sur l'axe x et y.
 */
public class Position {
    private int x = 0;
    private int y = 0;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the X coordinate.
     * @return the value of the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for the X coordiantes.
     * @param x the new value for X.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for the Y coordinate.
     * @return the value of the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for the Y coordiantes.
     * @param y the new value for Y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Translates this point, at location (x,y), by dx along the x axis and dy along the y axis so that it now represents the Position (x+dx,y+dy).
     * @param dx the distance to move this point along the X axis
     * @param dy the distance to move this point along the Y axis
     */
    public void translate(int dx, int dy){
        this.x += dx;
        this.y += dy;
    }

    /**
     * Returns a string representation of this position and its location in the (x,y) coordinate space. This method is intended to be used only for debugging purposes, and the unitMatrix and format of the returned string may vary between implementations. The returned string may be empty but may not be null.
     * @return a string representation of this Position
     */
    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Determines whether or not two points are equal. Two instances of Position are equal if the values of their x and y member fields, representing their position in the coordinate space, are the same.
     * @param o an object to be compared with this Position.
     * @return true if the object to be compared is an instance of Position and has the same values; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        //Auto generated hashCode
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
