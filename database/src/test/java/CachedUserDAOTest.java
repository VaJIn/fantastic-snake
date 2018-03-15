import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.daoimpl.CachedDAOFactory;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

public class CachedUserDAOTest {

    DAOFactory factory = new CachedDAOFactory();

    @Test
    void testPersistance() {
        //Checking that different ways of retrieving the same user give the same userEntity object.

        UserDAO userDAO = factory.getUserDAO();
        Optional<UserEntity> userEntityOptional = userDAO.getUser(1);

        Assertions.assertTrue(userEntityOptional.isPresent());
        UserEntity userEntityById = userEntityOptional.get();

        Optional<UserEntity> userEntityOptional1 = userDAO.getUser(1);
        Assertions.assertTrue(userEntityOptional1.isPresent());
        Assertions.assertTrue(userEntityById == userEntityOptional1.get());

        Optional<UserEntity> userEntityOptional2 = userDAO.getUserByAccountName("user1");
        Assertions.assertTrue(userEntityOptional2.isPresent());

        UserEntity userByAccountName = userEntityOptional2.get();
        Assertions.assertTrue(userByAccountName == userEntityById);

        Optional<UserEntity> userEntityOptional3 = userDAO.getUser("user1", "123456");
        Assertions.assertTrue(userEntityOptional3.isPresent());
        UserEntity userEntityByAccountNameAndPassword = userEntityOptional3.get();
        Assertions.assertTrue(userEntityByAccountNameAndPassword == userEntityById);

        Collection<UserEntity> userEntitiesByAlias = userDAO.getUserByAlias("alias1"); //user1 has alias1 as alias.
        //Finding user with id #1
        Optional<UserEntity> userEntityOptional4 = userEntitiesByAlias.stream().filter(userEntity -> userEntity.getId() == 1).findFirst();
        Assertions.assertTrue(userEntityOptional4.isPresent());
        UserEntity userEntityByAlias = userEntityOptional.get();
        Assertions.assertTrue(userEntityByAlias == userEntityById);
    }

    @Test
    void testAddUser() {
        //Adding a user, then checking we get the same UserEntity in return when retrieving it.
        UserEntity userEntity = new UserEntity();
        userEntity.setAccountName("newUserAccountName");
        userEntity.setPassword("123456");
        userEntity.setEmail("email@cache.fr");
        userEntity.setAlias("thisIsAnAlias");
        userEntity.setId(-1);

        UserDAO userDAO = factory.getUserDAO();
        Assertions.assertAll(() -> userDAO.addUser(userEntity));
        Assertions.assertNotEquals(-1, userEntity.getId());

        Optional<UserEntity> optional = userDAO.getUser(userEntity.getId());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertTrue(optional.get() == userEntity);


    }
}
