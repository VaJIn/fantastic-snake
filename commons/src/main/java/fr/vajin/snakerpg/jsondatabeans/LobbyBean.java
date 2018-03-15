package fr.vajin.snakerpg.jsondatabeans;

import java.util.Collection;

public class LobbyBean {

    private Collection<PlayerBean> players;
    private int gameMode;
    private String map;

    public LobbyBean(){

    }

    public Collection<PlayerBean> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<PlayerBean> players) {
        this.players = players;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }
}
