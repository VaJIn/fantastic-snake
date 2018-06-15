package fr.vajin.snakerpg.servlet.data.serializer;

import com.google.gson.*;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.lang.reflect.Type;

/**
 * UserEntity serializer that only serialize the public information about user (id, alias, snakes).
 */
public class PublicUserEntitySerializer implements JsonSerializer<UserEntity> {

    private static final String PROPERTY_ID = "id";
    private static final String PROPERTY_ALIAS = "alias";
    private static final String PROPERTY_SNAKE = "snakes";

    @Override
    public JsonElement serialize(UserEntity src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(PROPERTY_ALIAS, src.getAlias());
        jsonObject.addProperty(PROPERTY_ID, src.getId());

        final JsonArray jsonArraySnakes = new JsonArray();

        for (SnakeEntity snakeEntity : src.getSnakes()) {
            final JsonElement jsonSnake = context.serialize(snakeEntity, SnakeEntity.class);
            jsonArraySnakes.add(jsonSnake);
        }
        jsonObject.add(PROPERTY_SNAKE, jsonArraySnakes);
        return jsonObject;
    }
}
