package fr.univangers.vajin.gamemodel.field;

import fr.univangers.vrjlpv.snakerpg.gamemodel.Position;

public interface Field {
    FieldUnit getFieldUnits(Position position);

    int getWidth();

    int getHeight();

}
