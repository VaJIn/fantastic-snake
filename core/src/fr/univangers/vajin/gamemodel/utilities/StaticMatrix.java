package fr.univangers.vajin.gamemodel.utilities;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * Non-resizable implementation of the Matrix interface. The content of the matrix is stored in a List, and is row-major ordered.
 * This implementation is not thread-safe as it use an ArrayList to store the content of the matrix.
 */
public class StaticMatrix<E> extends AbstractMatrix<E> {



    List<E> content;

    /**
     * {@inheritDoc} Create an StaticMatrix of size rows * columns.
     */
    public StaticMatrix(int rows, int columns) {
        super(rows, columns);

        this.content = new ArrayList<>(this.rows * this.columns);

        for (int i = 0; i < rows * columns; ++i) {
            this.content.add(null);
        }
    }

    public StaticMatrix(@NotNull Matrix<E> other) {
        this.rows = other.getRowDimension();
        this.columns = other.getColumnDimension();

        if (other instanceof StaticMatrix) {
            StaticMatrix<E> otherStatic = (StaticMatrix) other;
            this.content = new ArrayList<>(otherStatic.content);
        }

        this.content = new ArrayList<>(this.rows * this.columns);

        for(int i = 0; i < rows; ++i){
            for (int j = 0; j < columns; ++j) {
                this.content.add(other.get(i, j));
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


    /**
     * {@inheritDoc}
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    @Override
    public E get(int rowIndex, int columnIndex) {
        return content.get(rowIndex * columns + columnIndex);
    }

    /**
     * {@inheritDoc}
     * @param rowIndex the row index of the element to set
     * @param columnIndex the column index of the element to set
     * @param element
     */
    @Override
    public void set(int rowIndex, int columnIndex, E element) {
        System.out.println("Set " + rowIndex + ", " + columnIndex + " -> " + element + " (" + (rowIndex * this.columns + columnIndex) + ")");
        this.content.set(rowIndex * this.columns + columnIndex, element);
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
