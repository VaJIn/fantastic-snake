package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import fr.univangers.vajin.GameConstants;
import fr.univangers.vajin.IO.TileMapReader;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.engine.EngineBuilder;
import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.WrongPlayersNumberException;
import fr.univangers.vajin.engine.entities.snake.SimpleSnake;
import fr.univangers.vajin.engine.entities.snake.Snake;

import java.util.Map;

public class GameLoadingScreen implements Screen, InputProcessor {

    private AssetManager assetManager;
    private SnakeRPG game;
    private String mapFileName;

    private SpriteBatch batch;

    private BitmapFont font;

    private float width;
    private float height;

    public GameLoadingScreen(SnakeRPG game, AssetManager assetManager, String mapFileName) {
        this.game = game;
        this.assetManager = assetManager;
        this.mapFileName = mapFileName;

        this.font = new BitmapFont();
        this.batch = new SpriteBatch();

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        Gdx.input.setInputProcessor(this);
    }

    public GameLoadingScreen(SnakeRPG game, AssetManager assetManager, String mapFileName, Map<String, Class> files) {
        this.game = game;
        this.assetManager = assetManager;
        this.mapFileName = mapFileName;

        this.font = new BitmapFont();
        this.batch = new SpriteBatch();

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();


        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

        assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(new InternalFileHandleResolver()));

        for (Map.Entry<String, Class> entry : files.entrySet()) {
            assetManager.load(entry.getKey(), entry.getValue());
        }


        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (assetManager.update()) {
            font.draw(batch, "Press any key to start", width / 2, height / 2 - 50);
        }

        float progress = assetManager.getProgress();

        font.draw(batch, "Progress : " + progress, width / 2, height / 2);

        batch.end();


    }

    @Override
    public boolean keyDown(int keycode) {
        if (assetManager.update()) {
            TileMapReader reader = new TileMapReader(assetManager.get(mapFileName));

            EngineBuilder classicEngineBuilder = new EngineBuilder(reader.getField(), 1);
            classicEngineBuilder.addSnake(0, new SimpleSnake());
            //   classicEngineBuilder.addSnake(1, new SimpleSnake());
            //   classicEngineBuilder.addSnake(2, new SimpleSnake());

            GameEngine classicEngine = null;
            try {
                classicEngine = classicEngineBuilder.build();
            } catch (WrongPlayersNumberException e) {
                e.printStackTrace();
            }


            game.setScreen(new GameScreen(game, reader, assetManager, classicEngine, mapFileName));
            dispose();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
