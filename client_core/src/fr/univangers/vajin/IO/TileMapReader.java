package fr.univangers.vajin.IO;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import fr.univangers.vajin.GameConstants;
import fr.univangers.vajin.snakerpg.engine.*;
import fr.univangers.vajin.snakerpg.engine.gamemodel.*;
import fr.univangers.vajin.snakerpg.engine.utilities.Matrix;
import fr.univangers.vajin.snakerpg.engine.utilities.StaticMatrix;

import java.util.ArrayList;
import java.util.List;

public class TileMapReader {

    Field field;
    List<Entity> objects;

    int mapWidth;
    int mapHeight;

    int tileHeight;
    int tileWidth;

    TiledMap tiledMap;

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TileMapReader(TiledMap map) {

        this.tiledMap = map;
        this.objects = new ArrayList<>();


        MapProperties properties = map.getProperties();

        this.mapWidth = properties.get(GameConstants.TileMap.WIDTH, Integer.class);
        this.mapHeight = properties.get(GameConstants.TileMap.HEIGHT, Integer.class);

        this.tileWidth = properties.get(GameConstants.TileMap.TILE_WIDTH, Integer.class);
        this.tileHeight = properties.get(GameConstants.TileMap.TILE_HEIGHT, Integer.class);

        System.out.println("mapWidth : " + mapWidth);
        System.out.println("mapHeight : " + mapHeight);

        Matrix<FieldUnit> fieldMatrix = new StaticMatrix<FieldUnit>(mapHeight, mapWidth);

        System.out.println("Matrix rows : " + fieldMatrix.getRowDimension());
        System.out.println("Matrix columns :" + fieldMatrix.getColumnDimension());

        TiledMapTileLayer terrainLayer = (TiledMapTileLayer) map.getLayers().get(GameConstants.TileMap.TERRAIN_LAYER);

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

                String fieldUnitString = tileProperties.get(GameConstants.TileMap.TILE_TYPE, String.class);

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

                this.field = new StaticField(fieldMatrix);

            }
        }
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
