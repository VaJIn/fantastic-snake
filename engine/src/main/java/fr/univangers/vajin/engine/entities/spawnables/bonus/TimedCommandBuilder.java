package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.function.Consumer;

public class TimedCommandBuilder {

    private Consumer<Snake> applyConsumer;
    private Consumer<Snake> cancelConsumer;
    private Snake snake;
    private Integer id;
    private Integer tick;
    private Integer revertingTime;

    public TimedCommandBuilder(){
        applyConsumer = null;
        cancelConsumer = null;
        id = null;
        tick = null;
    }


    public TimedCommandBuilder setApplyConsumer(Consumer<Snake> applyConsumer) {
        this.applyConsumer = applyConsumer;
        return this;
    }

    public TimedCommandBuilder setCancelConsumer(Consumer<Snake> cancelConsumer) {
        this.cancelConsumer = cancelConsumer;
        return this;
    }

    public TimedCommandBuilder setSnake(Snake snake) {
        this.snake = snake;
        return this;
    }

    public TimedCommandBuilder setTick(Integer tick) {
        this.tick = tick;
        return this;
    }

    public TimedCommandBuilder setRevertingTime(Integer revertingTime) {
        this.revertingTime = revertingTime;
        return this;
    }

    public TimedCommand buildApplyCommand(){

        //Returning null if a value hasn't be set so that the application crashes
        if (applyConsumer==null || cancelConsumer==null || tick==null){
            return null;
        }
        else{
            TimedCommand builtCommand = new TimedCommandImpl(snake, tick, applyConsumer, cancelConsumer, false);
            this.id = builtCommand.getId();
            return builtCommand;
        }

    }

    public TimedCommand buildRevertCommand(){

        //Returning null if a value hasn't be set so that the application crashes
        if (applyConsumer==null || cancelConsumer==null || tick==null || revertingTime==null || id==null){
            return null;
        }
        else{
            return new TimedCommandImpl(id, snake, tick+revertingTime, cancelConsumer, applyConsumer, true);
        }


    }




}
