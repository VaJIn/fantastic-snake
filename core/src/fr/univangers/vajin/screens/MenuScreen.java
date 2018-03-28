package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.univangers.vajin.SnakeRPG;

public class MenuScreen implements Screen {

    SnakeRPG parent;
    Stage stage;

    Texture background;
    SpriteBatch batch;

    public MenuScreen(SnakeRPG parent) {
        this.parent = parent;

        this.background = parent.getAssetManager().getManager().get("background/menu1280x720.jpg", Texture.class);

        this.batch = new SpriteBatch();

        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        //  table.setDebug(true);
        stage.addActor(table);

        Skin skin = parent.getUISkin();

        TextButton startSPGame = new TextButton("Single Player", skin);
        TextButton hostLocalMultiplayer = new TextButton("Host Local Multiplayer", skin);
        TextButton joinLocalMultiplayer = new TextButton("Join local Multiplayer", skin);
        TextButton playOnline = new TextButton("Play Online", skin);
        TextButton exit = new TextButton("Exit", skin);
        TextButton creditButton = new TextButton("Credit", skin);

        table.add(startSPGame).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(hostLocalMultiplayer).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(joinLocalMultiplayer).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(playOnline).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(creditButton).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(exit).fillX().uniformX();

        //Adding listener

        //Exit button make the close the application (duh)
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        startSPGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getGameLoadingScreen().setMapFileName("simple_map.tmx");
                parent.changeScreen(SnakeRPG.GAME_LOADING_SCREEN);
            }
        });

        joinLocalMultiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(SnakeRPG.DIRECT_CONNECTION_SCREEN);
            }
        });

        hostLocalMultiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(SnakeRPG.LOBBY_SCREEN);
            }
        });

        creditButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(SnakeRPG.CREDIT_SCREEN);
            }
        });

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();
        this.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch.end();


        this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
