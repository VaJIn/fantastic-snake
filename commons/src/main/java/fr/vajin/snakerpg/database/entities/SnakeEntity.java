package fr.vajin.snakerpg.database.entities;

import java.util.Arrays;
import java.util.Objects;

public class SnakeEntity {

    private int userId;
    private int id;
    private int snakeClassId;
    private String name;
    private int expPoint;
    private byte[] info;
    transient private UserEntity user;
    private SnakeClassEntity snakeClass;

    public SnakeEntity() {
        this.userId = -1;
        this.id = -1;
        this.name = "";
        this.expPoint = -1;
        info = new byte[0];
        this.user = new UserEntity();
        this.snakeClass = new SnakeClassEntity();
    }

    public SnakeEntity(int id, String name, int expPoint, byte[] info, UserEntity user, SnakeClassEntity snakeClass) {
        this.userId = user.getId();
        this.id = id;
        this.name = name;
        this.expPoint = expPoint;
        this.info = info;
        this.user = user;
        this.snakeClass = snakeClass;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getExpPoint() {
        return expPoint;
    }

    public void setExpPoint(int expPoint) {
        this.expPoint = expPoint;
    }

    public byte[] getInfo() {
        return info;
    }

    public void setInfo(byte[] info) {
        this.info = info;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {

        if (this.user != user) {
            this.user = user;
            this.userId = user.getId();
            user.addSnake(this);
        }
    }

    public SnakeClassEntity getSnakeClass() {
        return this.snakeClass;
    }

    public void setSnakeClass(SnakeClassEntity snakeClass) {
        this.snakeClass = snakeClass;
        this.setSnakeClassId(snakeClass.getId());
    }

    public int getSnakeClassId() {
        return snakeClassId;
    }

    public void setSnakeClassId(int snakeClassId) {
        this.snakeClassId = snakeClassId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnakeEntity that = (SnakeEntity) o;
        return userId == that.userId &&
                id == that.id &&
                expPoint == that.expPoint &&
                Objects.equals(name, that.name) &&
                Arrays.equals(info, that.info) &&
                Objects.equals(user, that.user);
    }

    @Override
    public String toString() {
        return "SnakeEntity{" +
                "userId=" + userId +
                ", id=" + id +
                ", snakeClassId=" + snakeClassId +
                ", name='" + name + '\'' +
                ", expPoint=" + expPoint +
                ", info=" + Arrays.toString(info) +
                ", snakeClass=" + snakeClass +
                '}';
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(userId, id, name, expPoint);
        result = 31 * result + Arrays.hashCode(info);
        return result;
    }
}
