package fr.vajin.snakerpg.gameroom.jsondatabeans;

public class GameStartBean {


    int gameMode;
    String map;

    public GameStartBean(){

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
