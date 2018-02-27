package fr.univangers.vajin.engine.entities.spawnables;

import com.google.common.collect.ImmutableList;
import fr.univangers.vajin.engine.utilities.RandomNumberGenerator;

import java.util.Collection;

/**
 * Abstract implementation of a spawnable items registery
 * Whatever the subclass implementation, the computing is done in this class
 */
public abstract class AbstractRegistry implements Registry {

    Collection<? extends SpawnableItem> itemsCollection;
    private RandomNumberGenerator randomNumberGenerator;
    private int weightsSum;

    public AbstractRegistry(Collection<? extends SpawnableItem> collection){
        this.itemsCollection = ImmutableList.copyOf(collection);
        this.randomNumberGenerator = new RandomNumberGenerator();

        //Computing the sum of the probabilistic weights of the contained items
        this.weightsSum = 0;
        for (SpawnableItem i : this.itemsCollection){
            weightsSum += i.getProbaWeight();
        }
    }

    @Override
    public SpawnableItem getRandom() {

        //drawing a number in between 0 and the sum of the weights minus one
        int randomNumber = randomNumberGenerator.inRange(0, this.weightsSum-1);

        int currentSum = 0;

        //returning the item corresponding to the drawn number
        for (SpawnableItem item : this.itemsCollection){
            currentSum += item.getProbaWeight();
            if (randomNumber < currentSum){
                return item;
            }

        }

        return null; //Should never happen
    }

    @Override
    public SpawnableItem getByName(String name) {
        for (SpawnableItem i : itemsCollection) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

}
