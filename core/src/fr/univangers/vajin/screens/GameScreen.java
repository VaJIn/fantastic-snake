package fr.univangers.vajin.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import fr.univangers.vajin.GameConstants;
import fr.univangers.vajin.IO.TileMapReader;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.engine.*;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.engine.field.Field;
import fr.univangers.vajin.screens.objectView.EntityView;

import java.util.*;

public class GameScreen implements Screen, GameEngineObserver, InputProcessor {

    final SnakeRPG game;
    private final String mapFileName;

    private TiledMap tiledMap;

    private Field field;

    private GameEngine engine;

    private OrthographicCamera camera;

    private TiledMapRenderer tiledMapRenderer;

    private SpriteBatch batch;

    private BitmapFont font;

    private Map<Integer, EntityView> entityViewMap;


    private AssetManager assetManager;

    private long lastTime;


    public GameScreen(SnakeRPG game, TileMapReader reader, AssetManager assetManager, GameEngine engine, String mapFileName) {

        this.game = game;

        this.tiledMap = reader.getTiledMap();

        this.font = new BitmapFont();

        this.batch = new SpriteBatch();

        this.field = reader.getField();

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        this.entityViewMap = new TreeMap<>();

        this.assetManager = assetManager;

        this.mapFileName = mapFileName;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        //Height is multiplied by aspect ratio

        camera.setToOrtho(false, reader.getMapWidth() * reader.getTileWidth(), reader.getMapHeight() * reader.getTileHeight());

        camera.update();
        this.engine = engine;

        for (Entity e : this.engine.getEntityCollection()) {
            EntityView view = new EntityView(e, assetManager.get(GameConstants.ATLAS_FILENAME, TextureAtlas.class), reader.getMapWidth(), reader.getMapHeight(), reader.getTileWidth(), reader.getTileHeight(), tiledMap);
            entityViewMap.put(e.getEntityId(), view);
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        long time = System.currentTimeMillis();

        if (time - lastTime > 1000 / GameConstants.TICKRATE) {
            lastTime = time;
            engine.computeTick();
        }


        Gdx.gl.glClearColor(141f / 255f, 196f / 255f, 53f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


        batch.begin();
        font.draw(batch, "FPS : " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        font.draw(batch, "Score : " + engine.getPlayerScore(0), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50);

        if (engine.isGameOver()) {
            font.draw(batch, "GAME OVER\nPress any key to go back to the menu", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        }

        batch.end();
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
        for (Map.Entry<Integer, EntityView> viewEntry : entityViewMap.entrySet()) {
            viewEntry.getValue().dispose();
        }
    }

    @Override
    public void notifyNewEntity(Entity object) {

    }

    @Override
    public void notifyRemovedEntity(Entity entity) {
        EntityView view = entityViewMap.get(entity.getEntityId());

        view.dispose();

        entityViewMap.remove(entity.getEntityId());
    }

    @Override
    public void notifyGameEnd() {

    }

    /* INPUT HANDLING METHOD */
    @Override
    public boolean keyDown(int keycode) {
        if (engine.isGameOver()) {
            game.changeScreen(SnakeRPG.MENU_SCREEN);
            dispose();
            return true;
        } else {
            switch (keycode) {
                case Input.Keys.Z:
                    engine.sendInput(0, Snake.GO_NORTH);
                    break;
                case Input.Keys.Q:
                    engine.sendInput(0, Snake.GO_WEST);
                    break;
                case Input.Keys.S:
                    engine.sendInput(0, Snake.GO_SOUTH);
                    break;
                case Input.Keys.D:
                    engine.sendInput(0, Snake.GO_EAST);
                    break;


                case Input.Keys.UP:
                    engine.sendInput(1, Snake.GO_NORTH);
                    break;
                case Input.Keys.LEFT:
                    engine.sendInput(1, Snake.GO_WEST);
                    break;
                case Input.Keys.DOWN:
                    engine.sendInput(1, Snake.GO_SOUTH);
                    break;
                case Input.Keys.RIGHT:
                    engine.sendInput(1, Snake.GO_EAST);
                    break;
            }
            return true;
        }

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
}
