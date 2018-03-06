package fr.univangers.vajin.engine.utilities;

import java.util.Stack;

public class SizedStack<K> extends Stack<K> {

    private int maxCapacity;

    public SizedStack(int maxCapacity){
        this.maxCapacity = maxCapacity;
    }

    @Override
    public K push(K k) {

        if (size()>=maxCapacity){
            remove(0);
        }

        return super.push(k);
    }


}
