package fr.univangers.vajin;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class SnakeRPGAssetManager {

    private AssetManager assetManager;

    private String uiSkinAtlas = "skin/clean-crispy-ui.atlas";

    private String gameImageAtlas = "snake.atlas";

    private String tileMapToLoad;

    public SnakeRPGAssetManager() {

        this.assetManager = new AssetManager();

        FileHandleResolver resolver = new InternalFileHandleResolver();

        this.assetManager.setLoader(Texture.class, new TextureLoader(resolver));
        this.assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
        this.assetManager.setLoader(TiledMap.class, new TmxMapLoader(resolver));

    }

    public void setMapToLoad(String map) {
        this.tileMapToLoad = map;
    }

    public void queueAddFonts() {

    }

    public void queueAddGameImages() {
        this.assetManager.load(gameImageAtlas, TextureAtlas.class);
    }

    public void queueAddSound() {

    }

    public void queueAddParticleEffects() {
    }

    public void queueUIAssets() {
        this.assetManager.load("background/menu1280x720.jpg", Texture.class);
        this.assetManager.load(uiSkinAtlas, TextureAtlas.class);
    }

    public void dispose() {
        assetManager.dispose();
    }

    public AssetManager getManager() {
        return assetManager;
    }

    public void queueAddSounds() {
    }

    public void queueAddMusic() {
    }

    public void queueLoadingScreenAssets() {
    }

    public void queueLoadingTileMap() {
        this.assetManager.load(tileMapToLoad, TiledMap.class);
    }
}
