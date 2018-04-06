package fr.univangers.vajin.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import fr.univangers.vajin.SnakeRPG;
import fr.vajin.snakerpg.jsondatabeans.LobbyBean;
import fr.vajin.snakerpg.jsondatabeans.PlayerBean;

import java.util.Iterator;

public abstract class LobbyScreen extends AbstractMenuScreen {

    private LobbyBean lobbyBean;

    private Table playerTable;

    @Override
    public void show() {
        this.playerTable = new Table();

        updateTable();
    }

    public LobbyScreen(SnakeRPG parent) {
        super(parent);
        this.lobbyBean = new LobbyBean();
    }

    public LobbyBean getLobbyBean() {
        return lobbyBean;
    }

    public void setLobbyBean(LobbyBean lobbyBean) {
        this.lobbyBean = lobbyBean;
        updateTable();
    }

    private void updateTable() {
        if (getParent().getScreen() == this) {

            Skin skin = this.getParent().getUISkin();

            playerTable.reset();

            Iterator<PlayerBean> it = this.lobbyBean.getPlayers().iterator();
            while (it.hasNext()) {
                PlayerBean playerBean = it.next();
                Label aliasLabel = new Label(playerBean.getAlias(), skin);
                Label snakeIdLabel = new Label(playerBean.getLocalId() + "", skin);
                playerTable.add(aliasLabel).fillX().colspan(3);
                playerTable.add(snakeIdLabel).fillX();
                if (it.hasNext()) {
                    playerTable.row().pad(10, 0, 0, 0);
                }
            }
        }
    }

    public Table getPlayerTable() {
        return playerTable;
    }
}
