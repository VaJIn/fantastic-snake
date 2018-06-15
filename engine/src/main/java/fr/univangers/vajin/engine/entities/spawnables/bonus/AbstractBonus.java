package fr.univangers.vajin.engine.entities.spawnables.bonus;


public abstract class AbstractBonus implements Bonus{

    private String resourceKey;
    private int probaWeight;
    private String name;
    private BonusTarget bonusTarget;
    protected int gain;
    protected int duration;

    public AbstractBonus(String resourceKey, int probaWeight, String name, BonusTarget target, int gain, int duration){
        this.resourceKey = resourceKey;
        this.probaWeight = probaWeight;
        this.name = name;
        this.bonusTarget = target;
        this.gain = gain;
        this.duration = duration;
    }

    @Override
    public int getProbaWeight() {
        return probaWeight;
    }

    @Override
    public String getResourceKey() {
        return resourceKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BonusTarget getBonusTarget(){
        return bonusTarget;
    }
}
