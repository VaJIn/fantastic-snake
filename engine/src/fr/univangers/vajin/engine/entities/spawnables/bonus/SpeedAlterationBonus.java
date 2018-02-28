package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Collection;

public class SpeedAlterationBonus extends AbstractBonus{

    public SpeedAlterationBonus(String resourceKey, int probaWeight, String name, BonusTarget target, int gain) {
        super(resourceKey, probaWeight, name, target, gain);
    }

    @Override
    public void apply(Collection<Snake> targets) {

        targets.forEach( (snake) -> {
            if (gain>0){
                snake.accelerate(gain);
            }
            else{
                snake.decelerate(-gain);
            }
        });

    }
}
