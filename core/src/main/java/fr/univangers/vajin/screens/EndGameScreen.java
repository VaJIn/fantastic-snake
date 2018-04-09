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

//        NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("ninepatches1.png")),
//                1, 1, 1, 1);
//        NinePatchDrawable background = new NinePatchDrawable(patch);
//        this.scoreboard.setBackground(background);

        layout.add(this.scoreboard);
        layout.row().pad(10, 0, 0, 0);

        TextButton backToLobby = new TextButton("Back to lobby", this.getApplication().getUISkin());
        backToLobby.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                getApplication().changeScreen(SnakeRPG.DISTANT_LOBBY_SCREEN);
            }
        });

        layout.add(backToLobby);

        this.getStage().addActor(layout);

        updateTable();
    }

    public void updateTable() {
        if (this.getApplication().getScreen() == this) {
            final GameEndBean gameEndBean = this.getApplication().getNetworkController().getGameEndBean();
            final Skin skin = this.getApplication().getUISkin();

            scoreboard.reset();

            if (gameEndBean == null) {
                this.getLogger().debug("No data available");
                scoreboard.add(new Label("No data available for last game", skin));
            } else {
                this.getLogger().debug("Data available");
                scoreboard.add(new Label("Name", skin));
                scoreboard.add(new Label("Killcount", skin));
                scoreboard.add(new Label("Deathcount", skin));
                for (GameParticipationBean gp : gameEndBean.getGameParticipations()) {
                    scoreboard.row().pad(10, 0, 0, 0);
                    scoreboard.add(new Label(gp.getPlayer().getAlias(), skin));
                    scoreboard.add(new Label(String.valueOf(gp.getKillCount()), skin));
                    scoreboard.add(new Label(String.valueOf(gp.getDeathCount()), skin));
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
