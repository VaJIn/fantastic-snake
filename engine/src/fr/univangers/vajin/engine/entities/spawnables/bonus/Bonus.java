package fr.univangers.vajin.engine.entities.spawnables.bonus;
import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Collection;

public interface Bonus{

    int getProbaWeight();

    String getResourceKey();

    String getName();

    BonusTarget getBonusTarget();

    void apply(Collection<Snake> targets);
}
