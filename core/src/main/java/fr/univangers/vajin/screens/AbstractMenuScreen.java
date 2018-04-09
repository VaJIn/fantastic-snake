package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.univangers.vajin.SnakeRPG;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractMenuScreen implements Screen {

    private final Texture background;
    private final SpriteBatch batch;
    private final SnakeRPG application;
    private Stage stage;

    private final Logger logger;

    protected AbstractMenuScreen(SnakeRPG parent) {
        this.application = parent;

        this.background = parent.getAssetManager().getManager().get("background/menu1280x720.jpg", Texture.class);

        this.batch = new SpriteBatch();

        this.stage = new Stage(new ScreenViewport());

        this.logger = LogManager.getLogger(this.getClass());
    }

    protected Stage getStage() {
        return stage;
    }

    protected SnakeRPG getApplication() {
        return application;
    }

    @Override
    public void show() {
        this.stage.clear();
        boolean debugModeActivated =
                this.getApplication().getAppPreferences().isDebugModeActivated();
        logger.debug("Showing screen  - " + (debugModeActivated ? "DEBUG MODE" : ""));
        this.stage.setDebugAll(debugModeActivated);
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
    public void dispose() {
        this.stage.dispose();
        this.stage.clear();
    }

    protected Logger getLogger() {
        return logger;
    }
}
