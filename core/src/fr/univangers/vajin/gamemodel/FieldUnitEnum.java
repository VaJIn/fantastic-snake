package fr.univangers.vajin.gamemodel;

public enum FieldUnitEnum implements FieldUnit {

    //FIELD(walkable, bonusSpawnArea, solid)
    WATER(false, false, false),
    GRASS(true, true, true),
    BARRENLAND(true, false, true),
    WALL(false, false, true);

    boolean walkable;
    boolean bonusSpawnArea;
    boolean solid;

    FieldUnitEnum(boolean walkable, boolean bonusSpawnArea, boolean solid) {
        this.walkable = walkable;
        this.bonusSpawnArea = bonusSpawnArea;
        this.solid = solid;
    }

    @Override
    public boolean isWalkable() {
        return walkable;
    }

    @Override
    public boolean isBonusSpawnArea() {
        return bonusSpawnArea;
    }

    @Override
    public boolean isSolid() {
        return solid;
    }
}
