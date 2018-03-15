package fr.vajin.snakerpg.jsondatabeans;

public class GameParticipationBean {

    private PlayerBean player;
    private int deathCount;
    private int killCount;

    public GameParticipationBean() {
    }

    public PlayerBean getPlayer() {
        return player;
    }

    public void setPlayer(PlayerBean player) {
        this.player = player;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }
}
