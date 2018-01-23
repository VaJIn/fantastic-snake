package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.istack.internal.NotNull;
import fr.univangers.vajin.IO.TileMapReader;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.gamemodel.*;
import fr.univangers.vajin.gamemodel.utilities.Position;
import fr.univangers.vajin.screens.objectView.SnakeView;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen, GameEngineObserver {

    final SnakeRPG game;

    TiledMap tiledMap;

    Field field;

    GameEngine engine;

    OrthographicCamera camera;

    TiledMapRenderer tiledMapRenderer;

    SpriteBatch batch;

    BitmapFont font;

    List<Actor> actorList;

    AssetManager assetManager;

    public GameScreen(@NotNull SnakeRPG game, @NotNull TiledMap tiledMap, @NotNull AssetManager assetManager, GameEngine engine) {

        this.game = game;

        this.tiledMap = tiledMap;

        this.font = new BitmapFont();

        this.batch = new SpriteBatch();

        TileMapReader reader = new TileMapReader(tiledMap);

        this.field = reader.getField();

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        this.actorList = new ArrayList<>();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        this.camera = new OrthographicCamera();
        //Height is multiplied by aspect ratio

        camera.setToOrtho(true, reader.getMapWidth() * reader.getTileWidth(), reader.getMapHeight() * reader.getTileHeight());
        camera.update();

        List<Position> pos = new ArrayList<>();
        Snake dummy = new DummySnake(100, 100, 100, 100, 100, new Position(32, 32));

        actorList.add(new SnakeView(assetManager.get("snake.atlas", TextureAtlas.class), dummy, 16, 16));

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        for (Actor a : actorList) {
            a.draw(batch, 1);
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

    }

    @Override
    public void notifyNewMutableObject(MutableObject object) {

    }

    @Override
    public void notifyGameEnd() {

    }
}
