package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Collection;
import java.util.function.Consumer;

public class InvisibilityBonus extends AbstractBonus {


    public InvisibilityBonus(String resourceKey, int probaWeight, String name, BonusTarget target, int gain, int duration) {
        super(resourceKey, probaWeight, name, target, gain, duration);
    }

    @Override
    public void buildCommands(Collection<Snake> targets, GameEngine ge) {

        for (Snake snake : targets){

            //Creating apply consumer
            Consumer<Snake> applyConsumer = (s -> {
                s.becomeInvisible();
            });

            //Creating cancel consumer
            Consumer<Snake> cancelConsumer = (s -> {
                s.stopInvisibility();
            });

            TimedCommandBuilder builder = new TimedCommandBuilder();

            //Creating the application command
            TimedCommand applicationCommand = builder.setApplyConsumer(applyConsumer)
                    .setCancelConsumer(cancelConsumer)
                    .setSnake(snake)
                    .setTick(ge.getCurrentTick())
                    .buildApplyCommand();

            ge.addBonusTimedCommand(applicationCommand);

            //Creating the reverting command after a definite time if necessary
            if (duration!=-1){

                TimedCommand revertingCommand = builder.setRevertingTime(duration)
                        .buildRevertCommand();

                ge.addBonusTimedCommand(revertingCommand);

            }




        }

    }
}
