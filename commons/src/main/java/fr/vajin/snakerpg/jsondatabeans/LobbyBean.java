package fr.vajin.snakerpg.jsondatabeans;

import fr.vajin.snakerpg.database.entities.GameModeEntity;

import java.util.ArrayList;
import java.util.Collection;

public class LobbyBean {

    private Collection<PlayerBean> players;
    private GameModeEntity gameMode;
    private String map;

    public LobbyBean(){
        this.players = new ArrayList<>();
        this.map = "";
    }

    public Collection<PlayerBean> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<PlayerBean> players) {
        this.players = players;
    }

    public GameModeEntity getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameModeEntity gameMode) {
        this.gameMode = gameMode;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }
}
