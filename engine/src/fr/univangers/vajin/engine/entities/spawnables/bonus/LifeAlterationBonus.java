package fr.univangers.vajin.engine.entities.spawnables.bonus;
import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Collection;

public class LifeAlterationBonus extends AbstractBonus {

    public LifeAlterationBonus(String resourceKey, int probaWeight, String name, BonusTarget target, int factor) {
        super(resourceKey, probaWeight, name, target, factor);
    }

    @Override
    public void apply(Collection<Snake> targets) {


    }
}
