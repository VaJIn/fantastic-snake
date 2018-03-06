package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Collection;

/**
 * Alters the size of the snake
 */
public class SizeAlterationBonus extends AbstractBonus{

    public SizeAlterationBonus(String resourceKey, int probaWeight, String name, BonusTarget target, int gain, int duration) {
        super(resourceKey, probaWeight, name, target, gain, duration);
    }

    @Override
    public void buildCommands(Collection<Snake> targets, GameEngine ge) {

        //Iterating over all the snakes the bonus should be applied to
        targets.forEach( (snake -> {


            //Creating apply command
            TimedCommand.BonusTimedLambda applyLambda = (s -> {
                s.changeSize(gain);
            });



            //Creating cancel command
            TimedCommand.BonusTimedLambda cancelLambda = (s -> {
                s.changeSize(-gain);
            });


            //Getting current tick of the application
            int applicationTick = ge.getCurrentTick();

            //Creating the command for applying the bonus during the next tick
            TimedCommand timedCommand =  new TimedCommandImpl(snake, applicationTick, applyLambda, cancelLambda);

            //Adding the apply command to the engine
            ge.addBonusTimedCommand(timedCommand);


            //If the bonus has a timeout, adding the reverting command after the specified duration
            if (duration!=-1){

                applicationTick+=duration;

                timedCommand =  new TimedCommandImpl(snake, applicationTick, cancelLambda, applyLambda);
                ge.addBonusTimedCommand(timedCommand);
            }


        }));
    }
}
