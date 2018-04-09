package fr.univangers.vajin.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.network.NetworkController;


public class HostLobbyScreen extends LobbyScreen {

    public HostLobbyScreen(SnakeRPG parent, NetworkController networkController) {
        super(parent, networkController);
    }

    @Override
    public void show() {

        super.show();

        Skin skin = this.getApplication().getUISkin();

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Table optionTable = new Table();

        TextButton exitLobby = new TextButton("Exit lobby", skin);
        TextButton ready = new TextButton("Ready", skin);

        SelectBox<String> mapSelectBox = new SelectBox<String>(skin);
        mapSelectBox.setItems("simple_map.tmx", "sample_map.tmx");

        mainTable.row().pad(0, 0, 0, 10);

        optionTable.add(new Label("Map", skin)).fillX().uniformX();
        optionTable.add(mapSelectBox).fillX().uniformX();
        optionTable.row().pad(10, 0, 0, 10);
        optionTable.add(new Label("Random option", skin));

        mainTable.add(optionTable);
        mainTable.add(this.getPlayerTable());
        mainTable.row();
        mainTable.add(exitLobby).fillX().uniformX();
        mainTable.add(ready).fillX().uniformX();

        exitLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        this.getStage().addActor(mainTable);

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
