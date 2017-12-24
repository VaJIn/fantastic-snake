package fr.univangers.vajin.gamemodel.field;

import fr.univangers.vajin.gamemodel.Position;

public interface Field {
    FieldUnit getFieldUnits(Position position);

    int getWidth();

    int getHeight();

}
