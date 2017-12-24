package fr.univangers.vajin.gamemodel.utilities;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Vector;
import java.util.function.Consumer;

/**
 * Non-resizable implementation of the Matrix interface.
 * @param <E>
 */
public class StaticMatrix<E> extends AbstractMatrix<E> {

    int rows;
    int columns;

    List<E> content;

    public StaticMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        this.content = new Vector<>(this.rows  * this.columns);
    }

    public StaticMatrix(Matrix<E> other){
        this.rows = other.getRowDimension();
        this.columns = other.getColumnDimension();

        this.content = new Vector<>(this.rows * this.columns);

        for(int i = 0; i < rows; ++i){
            for(int j = 0; j < rows; ++j){
                this.set(i, j, other.get(i, j));
            }
        }
    }

    @Override
    public int getRowDimension() {
        return this.rows;
    }

    @Override
    public int getColumnDimension() {
        return this.columns;
    }

    @Override
    public E get(int rowIndex, int columnIndex) {
        return content.get(rowIndex * columns + columnIndex);
    }

    @Override
    public void set(int rowIndex, int columnIndex, E element) {
        this.content.set(rowIndex * columns + columnIndex, element);
    }

    @Override
    public Iterator<E> iterator() {
        return content.iterator();
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        content.forEach(consumer);
    }

    @Override
    public Spliterator<E> spliterator() {
        return content.spliterator();
    }
}
