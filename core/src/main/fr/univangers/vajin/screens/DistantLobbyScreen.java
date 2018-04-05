package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.network.NetworkController;


public class DistantLobbyScreen extends LobbyScreen {

    private NetworkController networkController;


    public DistantLobbyScreen(SnakeRPG parent) {
        super(parent);
    }


    @Override
    public void show() {

        super.show();

        Skin skin = this.getParent().getUISkin();

        TextButton exitLobby = new TextButton("Exit lobby", skin);
        TextButton ready = new TextButton("Ready", skin);

        this.getStage().setDebugAll(true);

        SelectBox<String> mapSelectBox = new SelectBox<String>(skin);
        mapSelectBox.setItems("simple_map.tmx", "sample_map.tmx");

        exitLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getParent().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        this.getStage().addActor(this.getPlayerTable());

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