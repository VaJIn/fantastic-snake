package fr.univangers.vajin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.apache.logging.log4j.LogManager;

import java.util.NoSuchElementException;

public class SnakeRPGAssetManager {

    private AssetManager assetManager;


    private String gameImageAtlas = "snake.atlas";

    private String uiSkin;

    private String tileMapToLoad;

    private SnakeRPG application;

    public SnakeRPGAssetManager(SnakeRPG application) {

        this.application = application;

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

        if (this.uiSkin == null || this.uiSkin.trim().isEmpty()) {
            uiSkin = "clean-crispy";
        }

        FileHandle skinDirectory = Gdx.files.internal("skin/" + uiSkin);

        FileHandle[] atlas = skinDirectory.list((file, s) -> s.endsWith(".atlas"));

        LogManager.getLogger().debug("Found " + atlas.length + " atlas");

        if (atlas.length < 1) {
            throw new NoSuchElementException("No atlas found in skin directory" + uiSkin);
        }

        this.assetManager.load(atlas[0].path(), TextureAtlas.class);
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

    public void setUiSkin(String uiSkin) {
        this.uiSkin = uiSkin;
    }
}
