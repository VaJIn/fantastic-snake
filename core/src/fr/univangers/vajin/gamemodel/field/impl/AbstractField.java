package fr.univangers.vajin.gamemodel.field.impl;

import fr.univangers.vrjlpv.snakerpg.gamemodel.Position;
import fr.univangers.vrjlpv.snakerpg.gamemodel.field.Field;
import fr.univangers.vrjlpv.snakerpg.gamemodel.field.FieldUnit;
import fr.univangers.vrjlpv.snakerpg.gamemodel.utilities.Matrix;

public class AbstractField implements Field {

    protected Matrix<FieldUnit> unitMatrix;

    @Override
    public FieldUnit getFieldUnits(Position position) {
        return unitMatrix.get(position);
    }

    @Override
    public int getWidth() {
        return unitMatrix.getColumnDimension();
    }

    @Override
    public int getHeight() {
        return unitMatrix.getRowDimension();
    }
}
