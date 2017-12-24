package fr.univangers.vajin.gamemodel.utilities;

import fr.univangers.vrjlpv.snakerpg.gamemodel.Position;

public abstract class AbstractMatrix<E> implements Matrix<E> {

    /**
     * Call for the method {{@link Matrix<E>#get(int, int)}}.
     * @param p the position of the element to retrieve
     * @return
     */
    @Override
    public E get(Position p) {
        return get(p.getY(), p.getX());
    }

    /**
     * Call for the method {{@link Matrix<E>#set(E, int, int)}}
     * @param element
     * @param p
     */
    @Override
    public void set(E element, Position p) {
        this.set(p.getY(), p.getX(), element);
    }

}
