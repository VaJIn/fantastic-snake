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
import com.google.common.collect.Maps;
import fr.univangers.vajin.IO.TileMapReader;
import fr.univangers.vajin.engine.*;
import fr.univangers.vajin.engine.entities.snake.SimpleSnake;
import fr.univangers.vajin.screens.GameLoadingScreen;
import fr.univangers.vajin.screens.GameScreen;

import java.util.Map;

public class SnakeRPG extends Game implements ApplicationListener {

	AssetManager assetManager;


	@Override
	public void create () {

        String mapFileName = "hardcore.tmx";

		assetManager = new AssetManager();

		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

		assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(new InternalFileHandleResolver()));

        Map<String, Class> filesToLoad = Maps.newHashMap();

        filesToLoad.put(mapFileName, TiledMap.class);
        filesToLoad.put(GameConstants.ATLAS_FILENAME, TextureAtlas.class);

        this.setScreen(new GameLoadingScreen(this, assetManager, mapFileName, filesToLoad));
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
