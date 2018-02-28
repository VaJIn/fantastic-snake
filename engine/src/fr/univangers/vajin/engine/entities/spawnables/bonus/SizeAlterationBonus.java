package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Collection;

/**
 * Food bonus
 * The snake grows of the specified gain
 */
public class SizeAlterationBonus extends AbstractBonus{

    public SizeAlterationBonus(String resourceKey, int probaWeight, String name, BonusTarget target, int gain) {
        super(resourceKey, probaWeight, name, target, gain);
    }

    @Override
    public void apply(Collection<Snake> targets) {

        targets.forEach( (snake -> {

            if (gain>0){
                snake.grow(gain);
            }
            else{
                snake.shrink(-gain);
            }

        }));
    }
}
