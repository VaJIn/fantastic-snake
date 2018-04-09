package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.google.common.collect.Maps;
import fr.univangers.vajin.GameConstants;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.engine.GameEngine;
import fr.univangers.vajin.engine.GameEngineObserver;
import fr.univangers.vajin.engine.entities.Entity;
import fr.univangers.vajin.engine.entities.snake.Snake;
import fr.univangers.vajin.io.TileMapReader;
import fr.univangers.vajin.screens.objectView.EntityView;

import java.util.Map;

public class DistantGameScreen implements GameEngineObserver, InputProcessor, Screen {

    private SnakeRPG application;
    private GameEngine gameEngine;
    private Map<Integer, EntityView> entityViewMap;
    private TileMapReader reader;

    private OrthographicCamera camera;

    private TiledMapRenderer tiledMapRenderer;

    private SpriteBatch batch;

    private BitmapFont font;

    public DistantGameScreen(SnakeRPG application) {
        this.application = application;
    }

    public void setMap(String map) {

        this.reader = new TileMapReader(this.application.getAssetManager().getManager().get(map, TiledMap.class));
        this.font = new BitmapFont();
        this.entityViewMap = Maps.newHashMap();
    }

    public void setEngine(GameEngine engine) {
        this.cleanEntityView();

        this.gameEngine = engine;

        this.gameEngine.addGameEngineObserver(this);

        for (Entity e : this.gameEngine.getEntityCollection()) {
            EntityView view = new EntityView(e,
                    this.application.getAssetManager().getManager().get(GameConstants.ATLAS_FILENAME, TextureAtlas.class),
                    reader.getMapWidth(),
                    reader.getMapHeight(),
                    reader.getTileWidth(), reader.getTileHeight(),
                    reader.getTiledMap());
            entityViewMap.put(e.getEntityId(), view);
        }
    }

    private void cleanEntityView() {
        for (EntityView view : entityViewMap.values()) {
            view.dispose();
        }
        entityViewMap = Maps.newHashMap();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.Z:
                this.application.getNetworkController().sendInput(Snake.GO_NORTH);
                break;
            case Input.Keys.Q:
                this.application.getNetworkController().sendInput(Snake.GO_WEST);
                break;
            case Input.Keys.S:
                this.application.getNetworkController().sendInput(Snake.GO_SOUTH);
                break;
            case Input.Keys.D:
                this.application.getNetworkController().sendInput(Snake.GO_EAST);
                break;
        }
        return true;
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
    public void notifyNewEntity(Entity entity) {
        EntityView view = new EntityView(entity,
                this.application.getAssetManager().getManager().get(GameConstants.ATLAS_FILENAME, TextureAtlas.class),
                reader.getMapWidth(),
                reader.getMapHeight(),
                reader.getTileWidth(), reader.getTileHeight(),
                reader.getTiledMap()
        );

        entityViewMap.put(entity.getEntityId(), view);
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

    @Override
    public void show() {

        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(reader.getTiledMap());


        this.camera = new OrthographicCamera();
        camera.setToOrtho(
                false,
                reader.getMapWidth() * reader.getTileWidth(),
                reader.getMapHeight() * reader.getTileHeight()
        );

        camera.update();

        this.setEngine(this.application.getNetworkController().getDistantEngine());

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        long time = System.currentTimeMillis();

        if(gameEngine.isGameOver()){
            this.application.changeScreen(SnakeRPG.GAME_END_SCREEN);
        }

        Gdx.gl.glClearColor(141f / 255f, 196f / 255f, 53f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


        batch.begin();
        font.draw(batch, "FPS : " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        font.draw(batch, "Connected to " + this.application.getNetworkController().getCurrentServerAddress() + ":" + this.application.getNetworkController().getCurrentServerPort(), 0, 0);

        if (this.gameEngine.isGameOver()) {
            font.draw(batch, "GAME OVER\n", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
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
        this.batch.dispose();
        this.batch = null;

        this.tiledMapRenderer = null;
    }
}
