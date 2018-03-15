import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class UserDAOTest {

    UserDAO userDAO = DAOFactoryProvider.getDAOFactory().getUserDAO();

    @Test
    @DisplayName("Test UserDAO getUser(int id)")
    void testGetUserById() {
        Assertions.assertFalse(userDAO.getUser(-1).isPresent());

        Optional<UserEntity> optionalUser1 = userDAO.getUser(1);
        Assertions.assertTrue(optionalUser1.isPresent());

        UserEntity user1 = optionalUser1.get();

        Assertions.assertEquals(1, user1.getId());
        Assertions.assertEquals("user1", user1.getAccountName());
        Assertions.assertEquals("alias1", user1.getAlias());
        Assertions.assertEquals("user1@domain.fr", user1.getEmail());

        Collection<SnakeEntity> snakeEntities = user1.getSnakes();
        Assertions.assertNotNull(snakeEntities);
        Assertions.assertFalse(snakeEntities.isEmpty());
    }

    @Test
    @DisplayName("Test UserDAO getUser(String accountName,String hash)")
    void testGetUserByAccountNameAndHash() {
        Assertions.assertFalse(userDAO.getUser("", "").isPresent());

        //TODO modifier le password en hash
        Assertions.assertTrue(userDAO.getUser("user2", "123456").isPresent());
    }

    @Test
    @DisplayName("Test UserDAO getUserByAlias")
    void testGetUserByAlias() {
        Collection<UserEntity> users = userDAO.getUserByAlias("");
        Assertions.assertNotNull(users);
        Assertions.assertEquals(0, users.size());

        Collection<UserEntity> userByAlias = userDAO.getUserByAlias("alias3");

        Assertions.assertNotNull(userByAlias);
        Assertions.assertFalse(userByAlias.isEmpty());
    }

    @Test
    @DisplayName("Test UserDAO getUserByAccountName")
    void testGetUserByAccountName() {
        Assertions.assertFalse(userDAO.getUserByAccountName("").isPresent());

        Assertions.assertTrue(userDAO.getUserByAccountName("user2").isPresent());
    }

    @Test
    @Disabled
    @DisplayName("Test UserDAO addUser")
    void testInsertUser() {
        String validAccountName = "testInsertUser1";
        String email = "testInsertUser1@domain.fr";
        String alias = "aliasTestUser";
        String password = "123456";

        UserEntity entity = new UserEntity();
        entity.setAccountName(validAccountName);
        entity.setEmail(email);
        entity.setAlias(alias);
        entity.setPassword(password);

        Assertions.assertAll(() -> userDAO.addUser(entity));

        Assertions.assertThrows(SQLException.class, () -> userDAO.addUser(entity));

        Optional<UserEntity> retrievedOpt = userDAO.getUserByAccountName(validAccountName);

        Assertions.assertTrue(retrievedOpt.isPresent());
        UserEntity retrievedEntity = retrievedOpt.get();

        Assertions.assertEquals(validAccountName, retrievedEntity.getAccountName());
        Assertions.assertEquals(email, retrievedEntity.getEmail());
        Assertions.assertEquals(password, retrievedEntity.getPassword());
        Assertions.assertEquals(alias, retrievedEntity.getAlias());
    }
}
