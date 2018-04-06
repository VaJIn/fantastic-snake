package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.network.NetworkController;


public class DistantLobbyScreen extends LobbyScreen {

    public DistantLobbyScreen(SnakeRPG parent) {
        super(parent);
    }


    @Override
    public void show() {

        super.show();

        Skin skin = this.getParent().getUISkin();

        TextButton exitLobby = new TextButton("Exit lobby", skin);
        TextButton ready = new TextButton("Ready", skin);

        Table mainTable = new Table();

        mainTable.add(this.getPlayerTable()).colspan(2);

        mainTable.row();
        mainTable.add(exitLobby).fillX();
        mainTable.add(ready).fillX();

        this.getStage().setDebugAll(true);

        exitLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getParent().getNetworkController().stopNetwork();
                getParent().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        this.getStage().addActor(mainTable);

        Gdx.input.setInputProcessor(getStage());
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