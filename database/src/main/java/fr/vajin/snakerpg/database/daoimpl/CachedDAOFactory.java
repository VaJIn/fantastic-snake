package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.*;

import java.util.concurrent.TimeUnit;

public class CachedDAOFactory implements DAOFactory {

    private UserDAO userDAO;
    private SnakeDAO snakeDAO;
    private GameModeDAO gameModeDAO;
    private GameDAO gameDAO;
    private GameParticipationDAO gameParticipationDAO;
    private SnakeClassDAO snakeClassDAO;

    public CachedDAOFactory() {
        this.userDAO = new CachedUserDAO(new UserDAOImpl(this), this, 100, 10, TimeUnit.MINUTES);
        this.snakeDAO = new SnakeDAOImpl(this);
        this.gameModeDAO = new GameModeDAOImpl(this);
        this.gameDAO = new GameDAOImpl(this);
        this.gameParticipationDAO = new GameParticipationDAOImpl(this);
        this.snakeClassDAO = new SnakeClassDAOImpl(this);
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public SnakeDAO getSnakeDAO() {
        return snakeDAO;
    }

    @Override
    public GameModeDAO getGameModeDAO() {
        return gameModeDAO;
    }

    @Override
    public GameDAO getGameDAO() {
        return gameDAO;
    }

    @Override
    public SnakeClassDAO getSnakeClassDAO() {
        return snakeClassDAO;
    }

    @Override
    public GameParticipationDAO getGameParticipationDAO() {
        return gameParticipationDAO;
    }
}
