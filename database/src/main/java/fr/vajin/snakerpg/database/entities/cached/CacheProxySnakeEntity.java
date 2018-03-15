package fr.vajin.snakerpg.database.entities.cached;

import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.SnakeClassEntity;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.util.Optional;

public class CacheProxySnakeEntity extends SnakeEntity {

    transient private DAOFactory daoFactory;
    transient private boolean retrievedUser = false;
    transient private boolean retrievedSnakeClass = false;

    public CacheProxySnakeEntity(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public UserEntity getUser() {
        if (!retrievedUser) {
            UserDAO userDAO = daoFactory.getUserDAO();
            Optional<UserEntity> userEntityOptional = userDAO.getUser(this.getUserId());
            userEntityOptional.ifPresent(this::setUser);
            this.retrievedUser = true;
        }
        return super.getUser();
    }

    @Override
    public void setUser(UserEntity user) {
        super.setUser(user);
        retrievedUser = true;
    }

    @Override
    public SnakeClassEntity getSnakeClass() {
        if (!retrievedSnakeClass) {
            SnakeClassDAO snakeClassDAO = daoFactory.getSnakeClassDAO();
            Optional<SnakeClassEntity> snakeClassEntityOptional = snakeClassDAO.getSnakeClassById(this.getSnakeClassId());
            snakeClassEntityOptional.ifPresent(this::setSnakeClass);
            this.retrievedSnakeClass = true;
        }
        return super.getSnakeClass();
    }

    @Override
    public void setSnakeClass(SnakeClassEntity snakeClass) {
        super.setSnakeClass(snakeClass);
        retrievedSnakeClass = true;
    }
}
