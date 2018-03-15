import fr.vajin.snakerpg.database.DAOFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DAOFactoryTest {

    DAOFactory factory = DAOFactoryProvider.getDAOFactory();

    @Test
    @DisplayName("Test DAOFactory:getUserDAO()")
    void testGetUserDAO() {
        Assertions.assertNotNull(factory.getUserDAO());
    }

    @Test
    @DisplayName("Test DAOFactory:getSnakeDAO()")
    void testGetSnakeDAO() {
        Assertions.assertNotNull(factory.getSnakeDAO());
    }

    @Test
    @DisplayName("Test DAOFactory:getSnakeClassDAO()")
    void testGetSnakeClassDAO() {
        Assertions.assertNotNull(factory.getSnakeClassDAO());
    }

    @Test
    @DisplayName("Test DAOFactory:getGameDAO()")
    void testGetGameDAO() {
        Assertions.assertNotNull(factory.getGameDAO());
    }

    @Test
    @DisplayName("Test DAOFactory:getGameParticipationDAO()")
    void testGetGameParticipationDAO() {
        Assertions.assertNotNull(factory.getGameParticipationDAO());
    }

    @Test
    @DisplayName("Test DAOFactory:GameModeDAO()")
    void testGetGameModeDAO() {
        Assertions.assertNotNull(factory.getUserDAO());
    }
}
