package fr.vajin.snakerpg.database.entities.cached;

import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.util.Collection;
import java.util.Set;

public class CacheProxyUserEntity extends UserEntity {

    transient private DAOFactory daoFactory;
    transient private boolean retrievedSnake = false;

    public CacheProxyUserEntity(DAOFactory daoFactory) {
        super();
        this.daoFactory = daoFactory;
    }

    public CacheProxyUserEntity(DAOFactory daoFactory, int id, String alias, String email, String accountName, String password) {
        super(id, alias, email, accountName, password);
        this.daoFactory = daoFactory;
    }

    @Override
    public Set<SnakeEntity> getSnakes() {
        if (!retrievedSnake) {
            SnakeDAO snakeDAO = daoFactory.getSnakeDAO();
            Collection<SnakeEntity> snakeEntities = snakeDAO.getSnakeByUser(this.getId());
            this.setSnakes(snakeEntities);
        }
        return super.getSnakes();
    }

    @Override
    public void setSnakes(Collection<SnakeEntity> snakes) {
        super.setSnakes(snakes);
        retrievedSnake = true;
    }
}
