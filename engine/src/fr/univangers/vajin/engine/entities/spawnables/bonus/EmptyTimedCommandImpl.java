package fr.univangers.vajin.engine.entities.spawnables.bonus;

/**
 * Empty timed command that is associated with a tick but that does nothing
 */
public class EmptyTimedCommandImpl extends TimedCommandImpl{


    public EmptyTimedCommandImpl(int tick) {
        super(null, tick, null, null, false);
    }

    @Override
    public void apply() {
        //Nothing happens
    }

    @Override
    public void cancel() {
        //Nothing happens
    }

    @Override
    public TimedCommand clone() {
        return new EmptyTimedCommandImpl(getTick());
    }
}
