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
import fr.univangers.vajin.screens.GameScreen;

public class SnakeRPG extends Game implements ApplicationListener {
	SpriteBatch batch;
	AssetManager assetManager;


	@Override
	public void create () {
		assetManager = new AssetManager();

		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

		assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(new InternalFileHandleResolver()));

		assetManager.load("sample_map.tmx", TiledMap.class);

		assetManager.load("snake.atlas", TextureAtlas.class);

		assetManager.finishLoading();


		this.setScreen(new GameScreen(this, assetManager.get("sample_map.tmx"), assetManager, null));
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
