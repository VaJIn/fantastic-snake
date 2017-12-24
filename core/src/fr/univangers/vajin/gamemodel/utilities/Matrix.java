package fr.univangers.vajin.gamemodel.utilities;


import fr.univangers.vajin.gamemodel.Position;

public interface Matrix<E> extends Iterable<E>, Cloneable {
    /**
     * @return the number of getRowDimension of the matrix
     */
    int getRowDimension();

    /**
     * @return the number of getColumnDimension of the matrix
     */
    int getColumnDimension();

    /**
     * Return the element at line rowIndex and column columnIndex in the matrix.
     *
     * @param columnIndex
     * @param rowIndex
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index are out of range ( columnIndex < 0 || columnIndex >= getColumnDimension() || rowIndex < 0 || rowIndex >= getRowDimension()
     */
    E get(int rowIndex, int columnIndex);

    /**
     * Return the element at row p.getY() and column p.getX() in the matrix
     *
     * @param p the position of the element to retrieve
     * @return the element at the given position.
     */
    E get(Position p);


    void set(int rowIndex, int columnIndex, E element);

    /**
     *
     * @param element
     * @param p
     */
    void set(E element, Position p);
}