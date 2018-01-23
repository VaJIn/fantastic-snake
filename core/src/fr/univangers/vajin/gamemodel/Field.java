package fr.univangers.vajin.gamemodel;


import fr.univangers.vajin.gamemodel.utilities.Position;

public interface Field {
    FieldUnit getFieldUnits(Position position);

    int getWidth();

    int getHeight();

}
