package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import fr.univangers.vajin.SnakeRPG;

public class MenuScreen extends AbstractMenuScreen {

    public MenuScreen(SnakeRPG parent) {
        super(parent);
    }

    @Override
    public void show() {

        super.show();
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        //  table.setDebug(true);
        this.getStage().addActor(table);

        Skin skin = this.getApplication().getUISkin();

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
                getApplication().getGameLoadingScreen().setMapFileName("simple_map.tmx");
                getApplication().changeScreen(SnakeRPG.GAME_LOADING_SCREEN);
            }
        });

        joinLocalMultiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.DIRECT_CONNECTION_SCREEN);
            }
        });

        hostLocalMultiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.HOST_LOBBY_SCREEN);
            }
        });

        creditButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.CREDIT_SCREEN);
            }
        });

        Gdx.input.setInputProcessor(this.getStage());

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
}
