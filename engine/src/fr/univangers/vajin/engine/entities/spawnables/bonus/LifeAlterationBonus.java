package fr.univangers.vajin.engine.entities.spawnables.bonus;
import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Collection;

public class LifeAlterationBonus extends AbstractBonus {

    public LifeAlterationBonus(String resourceKey, int probaWeight, String name, BonusTarget target, int gain, int duration) {
        super(resourceKey, probaWeight, name, target, gain, duration);
    }

    @Override
    public void buildCommands(Collection<Snake> targets, GameEngine ge) {


    }
}
