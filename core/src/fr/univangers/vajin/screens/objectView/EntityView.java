package fr.univangers.vajin.screens.objectView;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.EntityObserver;
import fr.univangers.vajin.engine.utilities.Position;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EntityView implements EntityObserver {

    private Entity entity;

    private TextureAtlas atlas;

    private int tileWidth;
    private int tileHeight;

    TiledMapTileLayer layer;

    TiledMap map;

    private Map<Integer, Sprite> spritesMap;

    public EntityView(Entity entity, TextureAtlas atlas, int mapWidth, int mapHeight, int tileWidth, int tileHeight, TiledMap tileMap) {
        this.entity = entity;
        this.atlas = atlas;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        this.spritesMap = new HashMap<>();

        this.map = tileMap;

        this.layer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);

        entity.registerObserver(this);

        Iterator<Entity.EntityTileInfo> it = entity.getEntityTilesInfosIterator();

        while (it.hasNext()) {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            Entity.EntityTileInfo info = it.next();

            this.tileChange(info.getPosition(), info.getRessourceKey());

        }

        tileMap.getLayers().add(this.layer);
    }

    @Override
    public void notifyDestroyed(Entity entity) {
        layer.setOpacity(0.5f);
    }

    @Override
    public void notifyStateChange(Entity entity, int what) {
        //Do nothing
    }

    @Override
    public void notifyChangeAtPosition(Entity entity, Position position, int what) {
        if (what == Entity.ONE_LESS_COVER_ON_POSITION) {
            layer.setCell(position.getX(), position.getY(), new TiledMapTileLayer.Cell());
        }
    }

    @Override
    public void notifySpriteChange(int id, Position newPosition, String newResource) {
        if (newResource.equals("")) {
            return;
        }
        tileChange(newPosition, newResource);
    }

    private void tileChange(Position pos, String newRessource) {
//        if (layer.getCell(pos.getX(), pos.getY()) == null) {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(getTile(newRessource));
            layer.setCell(pos.getX(), pos.getY(), cell);
//        } else {
//            layer.getCell(pos.getX(), pos.getY()).setTile(getTile(newRessource));
//        }
    }

    private TiledMapTile getTile(String resource) {
        if (resource.equals("")) {
            return null;
        }
        return new StaticTiledMapTile(this.atlas.findRegion(resource));
    }

    public void dispose() {
        this.map.getLayers().remove(this.layer);
    }
}
