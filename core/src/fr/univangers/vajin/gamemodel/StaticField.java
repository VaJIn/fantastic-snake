package fr.univangers.vajin.gamemodel;

import fr.univangers.vajin.gamemodel.Field;
import fr.univangers.vajin.gamemodel.FieldUnit;
import fr.univangers.vajin.gamemodel.utilities.Matrix;
import fr.univangers.vajin.gamemodel.utilities.Position;
import fr.univangers.vajin.gamemodel.utilities.StaticMatrix;

public class StaticField implements Field {

    Matrix<FieldUnit> content;

    public StaticField(Matrix<FieldUnit> content) {
        this.content = new StaticMatrix<FieldUnit>(content);
    }

    @Override
    public FieldUnit getFieldUnits(Position position) {
        return this.content.get(position);
    }

    @Override
    public int getWidth() {
        return this.content.getColumnDimension();
    }

    @Override
    public int getHeight() {
        return this.content.getRowDimension();
    }
}
