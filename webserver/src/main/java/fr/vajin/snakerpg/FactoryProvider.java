package fr.vajin.snakerpg;

import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.daoimpl.CachedDAOFactory;

public class FactoryProvider {

    private static DAOFactory daoFactory = new CachedDAOFactory();

    public static DAOFactory getDAOFactory() {
        return daoFactory;
    }
}
