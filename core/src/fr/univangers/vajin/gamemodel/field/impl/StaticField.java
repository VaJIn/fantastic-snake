package fr.univangers.vajin.gamemodel.field.impl;

import fr.univangers.vrjlpv.snakerpg.gamemodel.field.FieldUnit;
import fr.univangers.vrjlpv.snakerpg.gamemodel.utilities.Matrix;
import fr.univangers.vrjlpv.snakerpg.gamemodel.utilities.StaticMatrix;

public class StaticField extends AbstractField {

    public StaticField(Matrix<FieldUnit> unitMatrix) {
        this.unitMatrix = new StaticMatrix<FieldUnit>(unitMatrix);
    }
}
