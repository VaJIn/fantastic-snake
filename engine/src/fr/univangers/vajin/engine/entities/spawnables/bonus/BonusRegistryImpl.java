package fr.univangers.vajin.engine.entities.spawnables.bonus;

import com.google.common.collect.ImmutableList;
import fr.univangers.vajin.engine.entities.spawnables.Registry;
import fr.univangers.vajin.engine.utilities.RandomNumberGenerator;

import java.util.Collection;

/**
 * Implementation of a registery for the bonuses
 */
public class BonusRegistryImpl implements Registry {

    private Collection<Bonus> bonusCollection;
    private RandomNumberGenerator randomNumberGenerator;
    private int weightsSum;

    public BonusRegistryImpl(Collection<Bonus> collection){
        this.bonusCollection = ImmutableList.copyOf(collection);
        this.randomNumberGenerator = new RandomNumberGenerator();

        //Computing the sum of the probabilistic weights of the contained items
        this.weightsSum = 0;
        for (Bonus b : this.bonusCollection){
            weightsSum += b.getProbaWeight();
        }
    }

    @Override
    public Bonus getRandom() {

        //drawing a number in between 0 and the sum of the weights minus one
        int randomNumber = randomNumberGenerator.inRange(0, this.weightsSum-1);

        int currentSum = 0;

        //returning the item corresponding to the drawn number
        for (Bonus b : this.bonusCollection){
            currentSum += b.getProbaWeight();
            if (randomNumber < currentSum){
                return b;
            }

        }

        return null; //Should never happen
    }

    @Override
    public Bonus getByName(String name) {
        for (Bonus b : bonusCollection) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }

}
