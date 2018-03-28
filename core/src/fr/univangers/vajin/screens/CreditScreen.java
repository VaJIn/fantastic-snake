package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import fr.univangers.vajin.SnakeRPG;

public class CreditScreen implements Screen {

    private SnakeRPG parent;
    private Stage stage;

    private SpriteBatch batch;
    private Texture background;

    private FileHandle creditFile;

    public CreditScreen(SnakeRPG parent) {
        this.parent = parent;
        this.stage = new Stage(new StretchViewport(1280, 720));

        this.stage.setDebugAll(true);

        this.batch = new SpriteBatch();
        this.background = parent.getAssetManager().getManager().get("background/menu1280x720.jpg", Texture.class);

        this.loadCreditText();
    }

    private void loadCreditText() {
        this.creditFile = Gdx.files.internal("credit.txt"); //TODO remove magic string :)
    }

    @Override
    public void show() {

        Skin skin = this.parent.getUISkin();

        Table layout = new Table();
        layout.setFillParent(true);

        stage.addActor(layout);

        TextButton backToMenu = new TextButton("Back to menu", skin);

        Label creditTxt = new Label("", skin);

        if (creditFile.exists()) {
            creditTxt.setText(creditFile.readString());
        } else {
            creditTxt.setText("Error : Could not find credit.txt in internal assets");
        }
        creditTxt.setAlignment(Align.center);

        layout.add(creditTxt).width(stage.getWidth() / 2);
        layout.row().pad(10, 0, 0, 0);
        layout.add(backToMenu);

        backToMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(SnakeRPG.MENU_SCREEN);
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


        stage.act();
        stage.draw();
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
        this.stage.dispose();
    }
}
