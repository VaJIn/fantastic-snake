package fr.vajin.snakerpg.database.entities;

import java.util.Objects;

public class GameParticipationEntity {

    private int idUser;
    private int idGame;
    private int score;
    private int killCount;
    private int deathCount;
    transient private GameEntity game;
    transient private UserEntity user;

    public GameParticipationEntity() {
        this.idUser = -1;
        this.idGame = -1;
        this.score = -1;
        this.killCount = -1;
        this.deathCount = -1;
        this.game = new GameEntity();
        this.user = new UserEntity();
    }

    public GameParticipationEntity(int idUser, int idGame, int score, int killCount, int deathCount, GameEntity game, UserEntity user) {
        this.idUser = idUser;
        this.idGame = idGame;
        this.score = score;
        this.killCount = killCount;
        this.deathCount = deathCount;
        this.game = game;
        this.user = user;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void setDeathCount(int deathCount) {
        this.deathCount = deathCount;
    }

    public GameEntity getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "GameParticipationEntity{" +
                "idUser=" + idUser +
                ", idGame=" + idGame +
                ", score=" + score +
                ", killCount=" + killCount +
                ", deathCount=" + deathCount +
                '}';
    }

    public void setGame(GameEntity game) {
        if (this.game != game) {
            this.game = game;
            this.idGame = game.getId();
            game.addGameParticipation(this);
        }
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameParticipationEntity that = (GameParticipationEntity) o;
        return idUser == that.idUser &&
                idGame == that.idGame &&
                score == that.score &&
                killCount == that.killCount &&
                deathCount == that.deathCount &&
                Objects.equals(game, that.game) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idUser, idGame, score, killCount, deathCount, game, user);
    }
}