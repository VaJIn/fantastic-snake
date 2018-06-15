import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.daoimpl.CachedDAOFactory;

public class DAOFactoryProvider {

    private static DAOFactory factory = new CachedDAOFactory();

    public static DAOFactory getDAOFactory() {
        return factory;
    }

}
