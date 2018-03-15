package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.*;


public class DAOFactoryImpl implements DAOFactory {

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl(this);
    }

    @Override
    public GameDAO getGameDAO() {
        return new GameDAOImpl(this);
    }

    @Override
    public GameModeDAO getGameModeDAO() {
        return new GameModeDAOImpl(this);
    }

    @Override
    public GameParticipationDAO getGameParticipationDAO() {
        return new GameParticipationDAOImpl(this);
    }

    @Override
    public SnakeClassDAO getSnakeClassDAO() {
        return new SnakeClassDAOImpl(this);
    }

    @Override
    public SnakeDAO getSnakeDAO() {
        return new SnakeDAOImpl(this);
    }

}
