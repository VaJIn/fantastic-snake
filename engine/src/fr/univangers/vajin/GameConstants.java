package fr.univangers.vajin;

public class GameConstants {

    public class TileMap {
        public static final String TERRAIN_LAYER = "terrain";

        public static final String WIDTH = "width";
        public static final String HEIGHT = "height";

        public static final String TILE_WIDTH = "tilewidth";
        public static final String TILE_HEIGHT = "tileheight";

        public static final String TILE_TYPE = "fieldUnit";
    }

    public class SnakeView {

        public String NORTH = "north";
        public String SOUTH = "south";
        public String EAST = "east";
        public String WEST = "west";
    }

    public static final String ATLAS_FILENAME = "snake.atlas";

    public static final int TICKRATE = 32;
}
