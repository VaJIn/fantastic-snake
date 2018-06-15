package fr.vajin.snakerpg.database.entities.cached;

import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameDAO;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;

import java.util.Optional;

public class CacheProxyGameParticipationEntity extends GameParticipationEntity {

    transient private DAOFactory daoFactory;
    transient private boolean retrievedGame;
    transient private boolean retrievedUser;

    public CacheProxyGameParticipationEntity(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.retrievedGame = false;
        this.retrievedUser = false;
    }

    @Override
    public GameEntity getGame() {
        if (!retrievedGame) {
            GameDAO gameDAO = daoFactory.getGameDAO();
            Optional<GameEntity> gameEntityOptional = gameDAO.getGame(this.getIdGame());
            gameEntityOptional.ifPresent(this::setGame);
        }
        return super.getGame();
    }

    @Override
    public void setGame(GameEntity game) {
        super.setGame(game);
        retrievedGame = true;
    }

    @Override
    public UserEntity getUser() {
        if (!retrievedUser) {
            UserDAO userDAO = daoFactory.getUserDAO();
            Optional<UserEntity> userEntityOptional = userDAO.getUser(this.getIdUser());
            userEntityOptional.ifPresent(this::setUser);
        }
        return super.getUser();
    }

    @Override
    public void setUser(UserEntity user) {
        super.setUser(user);
        retrievedUser = true;
    }
}
