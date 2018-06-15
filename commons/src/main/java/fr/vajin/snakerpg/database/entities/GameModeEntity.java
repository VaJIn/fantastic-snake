package fr.vajin.snakerpg.database.entities;

import java.util.Objects;

public class GameModeEntity {

    private int id;
    private String name;
    private int minPlayer;
    private int maxPlayer;

    public GameModeEntity() {
        this.id = -1;
        this.name = "";
        this.minPlayer = -1;
        this.maxPlayer = -1;
    }

    public GameModeEntity(int id, String name, int minPlayer, int maxPlayer) {
        this.id = id;
        this.name = name;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(int minPlayer) {
        this.minPlayer = minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameModeEntity that = (GameModeEntity) o;
        return id == that.id &&
                minPlayer == that.minPlayer &&
                maxPlayer == that.maxPlayer &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, minPlayer, maxPlayer);
    }
}
