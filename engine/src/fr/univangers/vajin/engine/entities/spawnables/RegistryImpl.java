package fr.univangers.vajin.engine.entities.spawnables;

import fr.univangers.vajin.engine.utilities.RandomNumberGenerator;

import java.util.List;

/**
 * Abstract implementation of a spawnable items registery
 * Whatever the subclass implementation, the computing is done in this class
 */
public abstract class RegistryImpl implements Registry {

    List<? extends SpawnableItem> itemsList;
    private RandomNumberGenerator randomNumberGenerator;
    private int weightsSum;

    public RegistryImpl(List<? extends SpawnableItem> list){
        this.itemsList = list;
        this.randomNumberGenerator = new RandomNumberGenerator();

        //Computing the sum of the probabilistic weights of the contained items
        this.weightsSum = 0;
        for (SpawnableItem i : this.itemsList){
            weightsSum += i.getProbaWeight();
        }
    }

    @Override
    public SpawnableItem getRandom() {

        //drawing a number in between 0 and the sum of the weights minus one
        int randomNumber = randomNumberGenerator.inRange(0, this.weightsSum-1);

        int currentSum = 0;

        //returning the item corresponding to the drawn number
        for (SpawnableItem item : this.itemsList){
            currentSum += item.getProbaWeight();
            if (randomNumber < currentSum){
                return item;
            }

        }

        return null; //Should never happen
    }

    @Override
    public SpawnableItem getByName(String name) {
        for (SpawnableItem i : itemsList) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

}
