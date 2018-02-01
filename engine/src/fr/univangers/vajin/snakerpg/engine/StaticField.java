package fr.univangers.vajin.snakerpg.engine;

import fr.univangers.vajin.snakerpg.engine.utilities.Matrix;
import fr.univangers.vajin.snakerpg.engine.utilities.Position;
import fr.univangers.vajin.snakerpg.engine.utilities.StaticMatrix;

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
