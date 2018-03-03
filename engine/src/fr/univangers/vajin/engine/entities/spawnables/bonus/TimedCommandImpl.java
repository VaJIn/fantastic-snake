package fr.univangers.vajin.engine.entities.spawnables.bonus;

import fr.univangers.vajin.engine.entities.snake.Snake;


public class TimedCommandImpl implements TimedCommand {

    private BonusTimedLambda lambdaApply;
    private BonusTimedLambda lambdaCancel;
    private Snake s;
    private int tick;

    public TimedCommandImpl(Snake s, int tick, BonusTimedLambda lambdaApply, BonusTimedLambda lambdaCancel){
        this.s = s;
        this.tick = tick;
        this.lambdaApply = lambdaApply;
        this.lambdaCancel = lambdaCancel;
    }

    @Override
    public void apply() {
        lambdaApply.op(s);
    }

    @Override
    public void cancel() {
        lambdaCancel.op(s);
    }

    @Override
    public int getTick() {
        return tick;
    }

    public int compareTo(Object o) {

        if (o instanceof TimedCommand){
            return tick - ((TimedCommand) o).getTick();
        }
        else{
            return 1;
        }

    }



}
