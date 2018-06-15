package fr.vajin.snakerpg.jsondatabeans;

import java.util.Collection;

public class GameEndBean {

    private Collection<GameParticipationBean> gameParticipations;

    public GameEndBean() {
    }

    public Collection<GameParticipationBean> getGameParticipations() {
        return gameParticipations;
    }

    public void setGameParticipations(Collection<GameParticipationBean> gameParticipations) {
        this.gameParticipations = gameParticipations;
    }
}
