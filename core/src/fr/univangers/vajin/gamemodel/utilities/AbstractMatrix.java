package fr.univangers.vajin.gamemodel.utilities;


/**
 * Begining of the implementation for a Matrix. This defined the method {@link Matrix#get(Position)} and {@link Matrix#set(Position, Object)} to
 * simple accessor of {@link Matrix#get(int, int)} and {@link Matrix#set(int, int, Object)}.
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

    /**
     * {@inheritDoc}
     *
     * This implementation use the {@link Matrix#get(int, int)} method.
     *
     * @param p the position of the element to retrieve
     * @return
     */
    @Override
    public E get(Position p) {
        return get(p.getY(), p.getX());
    }


    /**
     * {@inheritDoc}
     *
     * This implementation use the {@link Matrix#set(Position, E)} method.
     * @param p the position of the element to set
     * @param element
     */
    @Override
    public void set(Position p, E element) {
        this.set(p.getY(), p.getX(), element);
    }

}
