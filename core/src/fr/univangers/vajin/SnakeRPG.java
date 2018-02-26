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
import fr.univangers.vajin.engine.entities.snake.SimpleSnake;
import fr.univangers.vajin.screens.GameScreen;

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


        EngineBuilder classicEngineBuilder = new EngineBuilder(reader.getField(), 1);
        classicEngineBuilder.addSnake(0, new SimpleSnake());
        classicEngineBuilder.addSnake(1, new SimpleSnake());
        classicEngineBuilder.addSnake(2, new SimpleSnake());

        try {
            GameEngine classicEngine = classicEngineBuilder.build();
            this.setScreen(new GameScreen(this, reader, assetManager, classicEngine));
        } catch (WrongPlayersNumberException e) {
            e.printStackTrace();
        }



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
