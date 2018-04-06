package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Collection;
import java.util.function.Consumer;

public class TimeMachineBonus extends AbstractBonus{

    public TimeMachineBonus(String resourceKey, int probaWeight, String name, BonusTarget target, int gain, int duration) {
        super(resourceKey, probaWeight, name, target, gain, duration);
    }

    @Override
    public void buildCommands(Collection<Snake> targets, GameEngine ge) {

        //Creating apply command
        Consumer<Snake> consumerApply = (s -> {
            ge.launchTimeMachine();
        });


        //No cancel command for the time machine
        Consumer<Snake> consumerRevert = (s -> {

        });


        //Getting current tick of the application
        int applicationTick = ge.getCurrentTick();

        //Creating the command for applying the bonus during the next tick
        TimedCommand timedCommand =  new TimedCommandImpl(null, applicationTick, consumerApply, consumerRevert, false);

        //Adding the apply command to the engine
        ge.addBonusTimedCommand(timedCommand);
    }



}
