package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.network.NetworkController;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.jsondatabeans.LobbyBean;


public class DistantLobbyScreen extends LobbyScreen {

    private Label mapLabel;
    private Label gameModeLabel;

    private boolean gameStarting = false;

    public DistantLobbyScreen(SnakeRPG parent, NetworkController networkController) {
        super(parent, networkController);
    }


    @Override
    public void show() {

        super.show();

        Skin skin = this.getApplication().getUISkin();

        TextButton exitLobby = new TextButton("Exit lobby", skin);
        TextButton ready = new TextButton("Ready", skin);

        ready.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().getNetworkController().getPacketCreator().sendPlayerReady();
            }
        });

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Table infoTable = new Table();


        if (this.gameModeLabel == null) {
            this.gameModeLabel = new Label("undefined", skin);
        }

        if (this.mapLabel == null) {
            this.mapLabel = new Label("undefined", skin);
        }

        infoTable.add(new Label("Map : ", skin)).fillX().uniformX();
        infoTable.add(mapLabel).fillX().uniformX();

        infoTable.row().pad(10, 0, 0, 0);
        infoTable.add(new Label("Gamemode : ", skin)).fillX().uniformX();
        infoTable.add(this.gameModeLabel).fillX().uniformX();

        mainTable.add(infoTable).uniformX().fillX().colspan(2);
        mainTable.add(this.getPlayerTable()).uniformX().fillX().top().right().colspan(2);

        mainTable.row().pad(10, 0, 0, 0);
        mainTable.add(exitLobby).fillX();
        mainTable.add(ready).fillX().colspan(3);

        exitLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().getNetworkController().stopNetwork();
                getApplication().changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        this.getStage().addActor(mainTable);

        Gdx.input.setInputProcessor(getStage());
    }

    @Override
    public void updateTable() {
        super.updateTable();

        final LobbyBean lobbyBean = networkController.getLobbyBean();
        if (mapLabel != null) {
            String map = lobbyBean.getMap();
            if (map == null || map.trim().length() == 0) {
                mapLabel.setText("undefined");
            } else {
                mapLabel.setText(lobbyBean.getMap());
            }
        }

        if (gameModeLabel != null) {
            GameModeEntity gameModeEntity = lobbyBean.getGameMode();
            if (gameModeEntity != null) {
                gameModeLabel.setText(gameModeEntity.getName());
            } else {
                gameModeLabel.setText("undefined");
            }
        }
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
    public void render(float delta) {

        if (gameStarting) {
            this.getApplication().changeScreen(SnakeRPG.GAME_LOADING_SCREEN);
        }

        super.render(delta);
    }

    public void setGameStarting(boolean gameStarting) {
        this.gameStarting = gameStarting;
    }
}