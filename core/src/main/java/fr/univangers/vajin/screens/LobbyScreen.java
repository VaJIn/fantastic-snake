package fr.univangers.vajin.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import fr.univangers.vajin.SnakeRPG;
import fr.univangers.vajin.network.NetworkController;
import fr.vajin.snakerpg.jsondatabeans.LobbyBean;
import fr.vajin.snakerpg.jsondatabeans.PlayerBean;

import java.util.Iterator;

public abstract class LobbyScreen extends AbstractMenuScreen {

    protected final NetworkController networkController;
    private Table playerTable;

    @Override
    public void show() {
        super.show();
        this.playerTable = new Table();

        updateTable();
    }

    public LobbyScreen(SnakeRPG parent, NetworkController networkController) {
        super(parent);
        this.networkController = networkController;
    }

    public void updateTable() {
        if (getParent().getScreen() == this) {

            Skin skin = this.getParent().getUISkin();

            playerTable.reset();

            Iterator<PlayerBean> it = this.networkController.getLobbyBean().getPlayers().iterator();
            while (it.hasNext()) {
                PlayerBean playerBean = it.next();
                Label aliasLabel = new Label(playerBean.getAlias(), skin);
                playerTable.add(aliasLabel).fillX().colspan(3);
                if (it.hasNext()) {
                    playerTable.row().pad(10, 0, 0, 0);
                }
            }
        }
    }

    public Table getPlayerTable() {
        return playerTable;
    }

    @Override
    public void dispose() {
        super.dispose();
        this.playerTable = null;
    }
}
