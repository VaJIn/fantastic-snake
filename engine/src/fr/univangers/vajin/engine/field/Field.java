package fr.univangers.vajin.engine.field;


import fr.univangers.vajin.engine.utilities.Position;

public interface Field {
    FieldUnit getFieldUnits(Position position);

    int getWidth();

    int getHeight();

}
