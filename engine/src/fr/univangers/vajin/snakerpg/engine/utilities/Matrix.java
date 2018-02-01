package fr.univangers.vajin.snakerpg.engine.utilities;


/**
 * An interface for Matrix. A matrix is a two dimensional array.
 */
public interface Matrix<E> extends Iterable<E>, Cloneable {
    /**
     *
     * @return the number of rows in the matrix
     */
    int getRowDimension();

    /**
     * @return the number of columns in the matrix
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

    /**
     * Set the elements at index (rowIndex, columnIndex) to e
     *
     * @param rowIndex    the row index of the element to set
     * @param columnIndex the column index of the element to set
     * @param e           the new element at the position
     */
    void set(int rowIndex, int columnIndex, E e);

    /**
     * Set the element at position p (such as p.getX() is the column index and p.getY() the row index) to e
     * @param p the position of the element to set
     * @param e the new element at this position
     */
    void set(Position p, E e);
}