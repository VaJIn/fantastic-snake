package fr.univangers.vajin.screens.objectView;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import fr.univangers.vajin.gamemodel.Entity;
import fr.univangers.vajin.gamemodel.EntityObserver;
import fr.univangers.vajin.gamemodel.utilities.Position;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntityView extends Actor implements EntityObserver {

    private Entity entity;

    private TextureAtlas atlas;

    private int tileWidth;
    private int tileHeight;

    private Map<Integer, Sprite> spritesMap;

    public EntityView(Entity entity, TextureAtlas atlas, int tileWidth, int tileHeight) {
        this.entity = entity;
        this.atlas = atlas;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        this.spritesMap = new HashMap<>();

        Iterator<Entity.EntityTileInfo> it = entity.getEntityTilesInfosIterator();
        while (it.hasNext()) {
            Entity.EntityTileInfo info = it.next();
            spritesMap.put(info.getId(), createSprite(info.getPosition(), info.getRessourceKey()));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Map.Entry<Integer, Sprite> entry : spritesMap.entrySet()) {
            entry.getValue().draw(batch);
        }
    }

    @Override
    public void notifyDestroyed(Entity entity) {
        //Do nothing
    }

    @Override
    public void notifyStateChange(Entity entity, int what) {
        //Do nothing
    }

    @Override
    public void notifyChangeAtPosition(Entity entity, Position position, int what) {
        //Do nothing
    }

    @Override
    public void notifySpriteChange(int id, Position newPosition, String newResource) {
        spritesMap.put(id, createSprite(newPosition, newResource));
    }

    protected Sprite createSprite(Position pos, String resource) {
        System.out.println("loading ressource : " + resource);
        TextureRegion region = atlas.findRegion(resource);
        Sprite sprite = new Sprite(region);
        sprite.setPosition(pos.getX() * tileWidth, pos.getY() * tileHeight);
        sprite.setSize(tileWidth, tileHeight);
        return sprite;
    }


}
