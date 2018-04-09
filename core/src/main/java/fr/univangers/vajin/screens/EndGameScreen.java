package fr.univangers.vajin.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import fr.univangers.vajin.SnakeRPG;
import fr.vajin.snakerpg.jsondatabeans.GameEndBean;
import fr.vajin.snakerpg.jsondatabeans.GameParticipationBean;

public class EndGameScreen extends AbstractMenuScreen {

    Table scoreboard;

    public EndGameScreen(SnakeRPG parent) {
        super(parent);
    }

    @Override
    public void show() {
        super.show();

        Table layout = new Table();
        layout.setFillParent(true);

        this.scoreboard = new Table();
        this.scoreboard.setFillParent(true);

        layout.add(this.scoreboard).fillX();
        layout.row().pad(10, 0, 0, 0);

        TextButton backToLobby = new TextButton("Back to lobby", this.getApplication().getUISkin());
        backToLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.DISTANT_LOBBY_SCREEN);
            }
        });
    }

    public void updateTable() {
        if (this.getApplication().getScreen() == this) {
            final GameEndBean gameEndBean = this.getApplication().getNetworkController().getGameEndBean();
            final Skin skin = this.getApplication().getUISkin();
            if (gameEndBean == null) {
                scoreboard.add(new Label("No data available for last game", skin));
            } else {
                scoreboard.add(new Label("Name", skin)).fillX();
                scoreboard.add(new Label("Killcount", skin)).fillX();
                scoreboard.add(new Label("Deathcount", skin)).fillX();
                for (GameParticipationBean gp : gameEndBean.getGameParticipations()) {
                    scoreboard.row().pad(10, 0, 0, 0);
                    scoreboard.add(new Label(gp.getPlayer().getAlias(), skin)).fillX();
                    scoreboard.add(new Label(String.valueOf(gp.getKillCount()), skin)).fillX();
                    scoreboard.add(new Label(String.valueOf(gp.getDeathCount()), skin)).fillX();
                }
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
}
