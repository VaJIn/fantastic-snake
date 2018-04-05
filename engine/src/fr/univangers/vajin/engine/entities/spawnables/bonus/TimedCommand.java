package fr.univangers.vajin.engine.entities.spawnables.bonus;


/**
 * Interface of the class whose role is to apply and cancel bonus effects
 */
public interface TimedCommand extends Comparable, Cloneable{


    /**
     * Applies the command
     */
    void apply();

    /**
     * Applies the reverting command
     */
    void cancel();

    /**
     * Returns the tick the command must be applied at
     * @return
     */
    int getTick();

    /**
     * Returns the ID of the timed command
     * @return
     */
    int getId();

    /**
     * Delays the time the command must be applied at of the specified time
     * @param delayTime
     */
    void delayOf(int delayTime);

    /**
     * Returns true if the command is cancelling a bonus after a defined time
     * @return
     */
    boolean isRevertingTimedBonus();

    TimedCommand clone();

}
