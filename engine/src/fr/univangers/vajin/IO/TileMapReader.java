package fr.univangers.vajin.IO;

import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.field.Field;
import fr.univangers.vajin.engine.field.FieldUnit;
import fr.univangers.vajin.engine.field.FieldUnitEnum;
import fr.univangers.vajin.engine.field.StaticField;
import fr.univangers.vajin.engine.utilities.Matrix;
import fr.univangers.vajin.engine.utilities.StaticMatrix;

import java.util.ArrayList;
import java.util.List;

public class TileMapReader {

    public static final String TERRAIN_LAYER = "terrain";

    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";

    public static final String TILE_WIDTH = "tilewidth";
    public static final String TILE_HEIGHT = "tileheight";

    public static final String TILE_TYPE = "fieldUnit";

    private Field field;
    private List<Entity> objects;

    private int mapWidth;
    private int mapHeight;

    private int tileHeight;
    private int tileWidth;

    private TiledMap tiledMap;

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public static TileMapReader newTileMapReader(String mapFilePath) {
        TiledMap tiledMap = new TmxMapLoader(new ExternalFileHandleResolver()).load(mapFilePath);

        return new TileMapReader(tiledMap);
    }

    public TileMapReader(TiledMap map) {

        this.tiledMap = map;
        this.objects = new ArrayList<>();


        MapProperties properties = map.getProperties();

        this.mapWidth = properties.get(WIDTH, Integer.class);
        this.mapHeight = properties.get(HEIGHT, Integer.class);

        this.tileWidth = properties.get(TILE_WIDTH, Integer.class);
        this.tileHeight = properties.get(TILE_HEIGHT, Integer.class);

        System.out.println("mapWidth : " + mapWidth);
        System.out.println("mapHeight : " + mapHeight);

        Matrix<FieldUnit> fieldMatrix = new StaticMatrix<FieldUnit>(mapHeight, mapWidth);

        System.out.println("Matrix rows : " + fieldMatrix.getRowDimension());
        System.out.println("Matrix columns :" + fieldMatrix.getColumnDimension());

        TiledMapTileLayer terrainLayer = (TiledMapTileLayer) map.getLayers().get(TERRAIN_LAYER);

        if (terrainLayer == null) {
            System.err.println("ERROR TILEDMAP : NO \"terrain\" layer ! Taking first layer as replacement");
            terrainLayer = (TiledMapTileLayer) map.getLayers().get(0);
        }


        //CAREFUL :  iteration on HEIGHT (rows) first
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                TiledMapTileLayer.Cell cell = terrainLayer.getCell(i, j);

                TiledMapTile tile = cell.getTile();

                MapProperties tileProperties = tile.getProperties();

                String fieldUnitString = tileProperties.get(TILE_TYPE, String.class);

                //  System.out.println("Tile " + i + ", " + j + " -> " + fieldUnitString);

                if (fieldUnitString == null) {
                    fieldUnitString = "null";
                }
                //TODO : fieldUnitFactory
                //TEMPORARY CODE
                switch (fieldUnitString) {
                    case "grass":
                        fieldMatrix.set(j, i, FieldUnitEnum.GRASS);
                        break;
                    case "barren_land":
                        fieldMatrix.set(j, i, FieldUnitEnum.BARRENLAND);
                        break;
                    case "water":
                        fieldMatrix.set(j, i, FieldUnitEnum.WATER);
                        break;
                    default:
                        fieldMatrix.set(j, i, FieldUnitEnum.WALL);
                        break;
                }
                //END TEMPORATY CODE

            }
        }
        this.field = new StaticField(fieldMatrix);

    }

    public Field getField() {
        return field;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }
}
