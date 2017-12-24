package fr.univangers.vajin.gamemodel.field.impl;

import fr.univangers.vrjlpv.snakerpg.gamemodel.field.FieldUnit;

public enum FieldUnitEnumImpl implements FieldUnit {

    GRASS(true, true, false),
    ROCK(false, false, true),
    BARREN_LAND(true, false, false),
    WATER(false, false, false);

    boolean walkable;
    boolean bonusSpawnArea;
    boolean solid;

    FieldUnitEnumImpl(boolean walkable, boolean bonusSpawnArea, boolean solid) {
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
