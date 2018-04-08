package fr.univangers.vajin.IO;

import com.google.gson.*;
import fr.univangers.vajin.engine.field.Field;
import fr.univangers.vajin.engine.field.FieldUnit;
import fr.univangers.vajin.engine.field.FieldUnitEnum;
import fr.univangers.vajin.engine.field.StaticField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JSONFieldIO {

    private static final Logger logger = LogManager.getLogger(JSONFieldIO.class);

    public Field openStaticFieldJSON(String filePath) throws IOException {

        logger.debug("Opening file " + filePath);

        Path path = new File(filePath).toPath();

        logger.debug("Retrieving text");
        List<String> content = Files.readAllLines(path, Charset.defaultCharset());
        StringBuilder builder = new StringBuilder();
        for (String str : content) {
            builder.append(str);
        }

        GsonBuilder gsonBuilder = new GsonBuilder();

        JsonDeserializer<FieldUnit> fieldUnitJsonDeserializer = new JsonDeserializer<FieldUnit>() {
            @Override
            public FieldUnit deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String string = json.getAsJsonPrimitive().getAsString();

                return FieldUnitEnum.valueOf(string);
            }
        };

        gsonBuilder.registerTypeAdapter(FieldUnit.class, fieldUnitJsonDeserializer);

        Gson gson = gsonBuilder.create();

        return gson.fromJson(builder.toString(), StaticField.class);
    }

}
