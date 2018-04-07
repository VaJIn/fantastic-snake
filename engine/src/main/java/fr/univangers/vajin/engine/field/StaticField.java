package fr.univangers.vajin.engine.field;

import fr.univangers.vajin.engine.utilities.Matrix;
import fr.univangers.vajin.engine.utilities.Position;
import fr.univangers.vajin.engine.utilities.StaticMatrix;

public class StaticField extends StaticMatrix<FieldUnit> implements Field {


    public StaticField(Matrix<FieldUnit> content) {
        super(content);
    }

    @Override
    public FieldUnit getFieldUnits(Position position) {
        return this.get(position);
    }

    @Override
    public int getWidth() {
        return this.getColumnDimension();
    }

    @Override
    public int getHeight() {
        return this.getRowDimension();
    }

    @Override
    public boolean containsPos(Position position) {
        return position.getX()>=0 && position.getX()<this.getWidth() && position.getY()>=0 && position.getY()<this.getHeight();
    }
}
