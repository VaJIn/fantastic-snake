package fr.univangers.vajin.snakerpg.engine;


import fr.univangers.vajin.snakerpg.engine.utilities.Position;

public interface Field {
    FieldUnit getFieldUnits(Position position);

    int getWidth();

    int getHeight();

}
