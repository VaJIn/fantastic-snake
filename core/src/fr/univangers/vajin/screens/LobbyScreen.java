package fr.univangers.vajin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import fr.univangers.vajin.SnakeRPG;
import fr.vajin.snakerpg.jsondatabeans.LobbyBean;
import fr.vajin.snakerpg.jsondatabeans.PlayerBean;

import javax.xml.crypto.Data;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Iterator;


public class LobbyScreen implements Screen {

    SnakeRPG game;

    LobbyBean lobbyBean;

    Stage stage;
    Table playerTable;

    DatagramSocket datagramSocket;


    public LobbyScreen(SnakeRPG game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(1280, 768));

        Gdx.input.setInputProcessor(stage);

        this.lobbyBean = new LobbyBean();

    }

    @Override
    public void show() {

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Table optionTable = new Table();

        TextButton exitLobby = new TextButton("Exit lobby", game.getUISkin());

        playerTable = new Table();

        setLobbyBean(this.getLobbyBean());

        stage.setDebugAll(true);
        stage.addActor(mainTable);

        SelectBox<String> mapSelectBox = new SelectBox<String>(game.getUISkin());
        mapSelectBox.setItems("simple_map.tmx", "sample_map.tmx");

        mainTable.row().pad(0, 0, 0, 10);

        optionTable.add(new Label("Map", game.getUISkin())).fillX().uniformX();
        optionTable.add(mapSelectBox).fillX().uniformX();
        optionTable.row().pad(10, 0, 0, 10);
        optionTable.add(new Label("Random option", game.getUISkin()));

        mainTable.add(optionTable);
        mainTable.add(playerTable).colspan(2).fillX();
        mainTable.row();
        mainTable.add(exitLobby).fillX().uniformX();

        exitLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(SnakeRPG.MENU_SCREEN);
            }
        });

        Gdx.input.setInputProcessor(stage);

    }

    private void updateTable() {
        if (game.getScreen() == this) {
            playerTable.reset();

            Iterator<PlayerBean> it = this.lobbyBean.getPlayers().iterator();
            while (it.hasNext()) {
                PlayerBean playerBean = it.next();
                TextButton aliasLabel = new TextButton(playerBean.getAlias(), game.getUISkin());
                TextButton snakeIdLabel = new TextButton(playerBean.getLocalId() + "", game.getUISkin());
                playerTable.add(aliasLabel).fillX().uniformX();
                playerTable.add(snakeIdLabel).fillX().uniformX();
                if (it.hasNext()) {
                    playerTable.row().pad(10, 0, 0, 0);
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        stage.dispose();
    }

    public LobbyBean getLobbyBean() {
        return lobbyBean;
    }

    public void setLobbyBean(LobbyBean lobbyBean) {
        this.lobbyBean = lobbyBean;
        updateTable();
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }
}
