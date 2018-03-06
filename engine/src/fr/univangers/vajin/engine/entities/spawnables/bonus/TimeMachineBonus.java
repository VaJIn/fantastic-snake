package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Collection;

public class TimeMachineBonus extends AbstractBonus{

    public TimeMachineBonus(String resourceKey, int probaWeight, String name, BonusTarget target, int gain, int duration) {
        super(resourceKey, probaWeight, name, target, gain, duration);
    }

    @Override
    public void buildCommands(Collection<Snake> targets, GameEngine ge) {

        //Creating apply command
        TimedCommand.BonusTimedLambda applyLambda = (s -> {
            ge.launchTimeMachine();
        });


        //No cancel command for the time machine
        TimedCommand.BonusTimedLambda cancelLambda = (s -> {

        });


        //Getting current tick of the application
        int applicationTick = ge.getCurrentTick();

        //Creating the command for applying the bonus during the next tick
        TimedCommand timedCommand =  new TimedCommandImpl(null, applicationTick, applyLambda, cancelLambda);

        //Adding the apply command to the engine
        ge.addBonusTimedCommand(timedCommand);
    }



}
