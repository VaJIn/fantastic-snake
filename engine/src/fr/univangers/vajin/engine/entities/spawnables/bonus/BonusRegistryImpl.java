package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.spawnables.AbstractRegistry;

import java.util.List;

/**
 * Stores a list of bonuses
 * The bonuses can be accessed randomly depending on their probability weight, or can be accessed by their name
 */
public class BonusRegistryImpl extends AbstractRegistry {

    public BonusRegistryImpl(List<Bonus> list) {
        super(list);
    }

    @Override
    public Bonus getRandom() {
        return ((Bonus)super.getRandom());
    }

    @Override
    public Bonus getByName(String name) {
        return ((Bonus) super.getByName(name));
    }
}
