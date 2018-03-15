package fr.univangers.vajin.engine.field;

import fr.vajin.snakerpg.utilities.Matrix;
import fr.univangers.vajin.engine.utilities.Position;
import fr.vajin.snakerpg.utilities.StaticMatrix;

public class StaticField implements Field {

    Matrix<FieldUnit> content;

    public StaticField(Matrix<FieldUnit> content) {
        this.content = new StaticMatrix<FieldUnit>(content);
    }

    @Override
    public FieldUnit getFieldUnits(Position position) {
        return this.content.get(position.getX(), position.getY());
    }

    @Override
    public int getWidth() {
        return this.content.getColumnDimension();
    }

    @Override
    public int getHeight() {
        return this.content.getRowDimension();
    }

    @Override
    public boolean containsPos(Position position) {
        return position.getX()>=0 && position.getX()<this.getWidth() && position.getY()>=0 && position.getY()<this.getHeight();
    }
}
