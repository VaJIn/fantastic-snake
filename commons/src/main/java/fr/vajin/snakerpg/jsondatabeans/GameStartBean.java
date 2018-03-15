package fr.vajin.snakerpg.jsondatabeans;

import fr.vajin.snakerpg.database.entities.GameModeEntity;

public class GameStartBean {


    private GameModeEntity gameMode;
    private String map;

    public GameStartBean(){

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
