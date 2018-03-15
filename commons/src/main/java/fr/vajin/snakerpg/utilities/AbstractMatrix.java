package fr.vajin.snakerpg.utilities;


/**
 * Begining of the implementation for a Matrix.
 */
public abstract class AbstractMatrix<E> implements Matrix<E> {


    protected int rows;
    protected int columns;

    public AbstractMatrix() {
        this.rows = 0;
        this.columns = 0;
    }

    public AbstractMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public AbstractMatrix(AbstractMatrix<E> other) {
        this.rows = other.rows;
        this.columns = other.columns;
    }

    /**
     * {@inheritDoc}
     * @return the number of rows in the matrix
     */
    @Override
    public int getRowDimension() {
        return rows;
    }

    /**
     * {@inheritDoc}
     *
     * @return the number of columns in the matrix
     */
    @Override
    public int getColumnDimension() {
        return columns;
    }


}
