package fr.univangers.vajin;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import fr.univangers.vajin.IO.TileMapReader;
import fr.univangers.vajin.engine.*;
import fr.univangers.vajin.engine.utilities.Direction;
import fr.univangers.vajin.engine.utilities.Position;
import fr.univangers.vajin.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class SnakeRPG extends Game implements ApplicationListener {
	SpriteBatch batch;
	AssetManager assetManager;


	@Override
	public void create () {

        String mapFileName = "simple_map.tmx";

		assetManager = new AssetManager();

		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

		assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(new InternalFileHandleResolver()));

        assetManager.load(mapFileName, TiledMap.class);

        assetManager.load(GameConstants.ATLAS_FILENAME, TextureAtlas.class);

		assetManager.finishLoading();

        TileMapReader reader = new TileMapReader(assetManager.get(mapFileName));

        List<Position> pos = new ArrayList<>();
        pos.add(new Position(15, 30));
        pos.add(new Position(16, 30));
        pos.add(new Position(17, 30));

        Snake snake = new SimpleSnake(pos, Direction.EAST);

        List<Entity> entities = new ArrayList<>();
        entities.add(new FoodSpawner(1, 3, new SimpleFoodRegistryImpl()));

        GameEngine engine = new SinglePlayerEngine(snake, entities, reader.getField());

        this.setScreen(new GameScreen(this, reader, assetManager, engine));
    }

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		assetManager.dispose();
	}
}
