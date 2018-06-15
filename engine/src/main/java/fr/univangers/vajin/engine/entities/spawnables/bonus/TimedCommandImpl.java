package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.function.Consumer;


/**
 * Each timed command has a ID that is usually unique
 * If two timed command are associated with a same action (example : bonus with annulation after timeout), they share
 * the same ID
 */
public class TimedCommandImpl implements TimedCommand {

    private static int ID = 0;

    private Consumer<Snake> consumerApply;
    private Consumer<Snake> consumerRevert;
    private Snake s;
    private int tick;
    private int id;
    private boolean isRevertingTimedBonus;


    /**
     * Constructor that creates a TimedCommand with the next available ID
     * @param s
     * @param tick
     * @param consumerApply
     * @param consumerRevert
     */
    public TimedCommandImpl(Snake s, int tick, Consumer<Snake> consumerApply, Consumer<Snake> consumerRevert, boolean isRevertingTimedBonus){
        this.s = s;
        this.tick = tick;
        this.consumerApply = consumerApply;
        this.consumerRevert = consumerRevert;
        this.id = ++ID;
        this.isRevertingTimedBonus = isRevertingTimedBonus;
    }

    /**
     * Constructor that creates a TimedCommand from an existing ID
     * Used for the commands that are related to another one and that have already been created
     * @param id
     * @param s
     * @param tick
     * @param consumerApply
     * @param consumerRevert
     */
    public TimedCommandImpl(int id, Snake s, int tick, Consumer<Snake> consumerApply, Consumer<Snake> consumerRevert, boolean isRevertingTimedBonus){
        this.s = s;
        this.tick = tick;
        this.consumerApply = consumerApply;
        this.consumerRevert = consumerRevert;
        this.id = id;
        this.isRevertingTimedBonus = isRevertingTimedBonus;

    }

    @Override
    public void apply() {
        consumerApply.accept(s);
    }

    @Override
    public void cancel() {
        consumerRevert.accept(s);
    }

    @Override
    public int getTick() {
        return tick;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void delayOf(int delayTime) {
        this.tick+=delayTime;
    }

    @Override
    public boolean isRevertingTimedBonus() {
        return this.isRevertingTimedBonus;
    }

    @Override
    public int compareTo(Object o) {

        if (o instanceof TimedCommand){
            return tick - ((TimedCommand) o).getTick();
        }
        else{
            return 1;
        }

    }


    @Override
    public TimedCommand clone(){
        return new TimedCommandImpl(id, s, tick, consumerApply , consumerRevert, isRevertingTimedBonus);
    }
}
