import fr.univangers.vajin.IO.JSONFieldIO;
import fr.univangers.vajin.engine.field.Field;
import fr.univangers.vajin.engine.field.FieldUnit;
import fr.univangers.vajin.engine.utilities.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class JSONFieldIOTest {

    @Test
    void testOpenJSONFile() throws IOException {

        String file = "simple_map.tmx.json";

        JSONFieldIO jsonFieldIO = new JSONFieldIO();

        Field field = jsonFieldIO.openStaticFieldJSON(file);

        Assertions.assertEquals(32, field.getHeight());
        Assertions.assertEquals(32, field.getWidth());

        for (int i = 0; i < 32; ++i) {
            for (int j = 0; j < 32; ++j) {
                Assertions.assertNotNull(field.getFieldUnits(new Position(i, j)));
            }
        }
    }

}
