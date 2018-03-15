import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.entities.SnakeClassEntity;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class SnakeDAOTest {

    SnakeDAO snakeDAO = DAOFactoryProvider.getDAOFactory().getSnakeDAO();

    @Test
    @DisplayName("Test SnakeDAO getSnakeById")
    void testGetSnakeById() {
        Assertions.assertFalse(snakeDAO.getSnakeById(-1).isPresent());

        Assertions.assertTrue(snakeDAO.getSnakeById(1).isPresent());
    }

    @Test
    @DisplayName("Test SnakeDAO getSnakeByUser")
    void testGetSnakeByUser() {
        Collection<SnakeEntity> snakes = snakeDAO.getSnakeByUser(-1);
        Assertions.assertNotNull(snakes);
        Assertions.assertEquals(0, snakes.size());

        snakes = snakeDAO.getSnakeByUser(1);
        Assertions.assertNotNull(snakes);
        Assertions.assertEquals(2, snakes.size());

        Iterator<SnakeEntity> it = snakes.iterator();

        UserEntity user = it.next().getUser();
        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getId());
        while (it.hasNext()) {
            UserEntity user2 = it.next().getUser();
            Assertions.assertNotNull(user2);
            Assertions.assertEquals(user, user2);
        }
    }

    @Test
    @DisplayName("Test SnakeDAO addSnake")
    void testAddSnake() {

        String name = "nameTestSnake";
        int expPoint = 200;
        byte[] info = null;
        Optional<UserEntity> user = DAOFactoryProvider.getDAOFactory().getUserDAO().getUser(2);
        Optional<SnakeClassEntity> snakeClass = DAOFactoryProvider.getDAOFactory().getSnakeClassDAO().getSnakeClassById(1);

        SnakeEntity snake = new SnakeEntity();
        snake.setName(name);
        snake.setExpPoint(expPoint);
        snake.setInfo(info);
        snake.setUser(user.get());
        snake.setSnakeClass(snakeClass.get());

        Collection<SnakeEntity> snakesBeforeInsert = snakeDAO.getSnakeByUser(user.get().getId());

        Assertions.assertAll(() -> snakeDAO.addSnake(snake));

        Collection<SnakeEntity> snakesAfterInsert = snakeDAO.getSnakeByUser(user.get().getId());

        Assertions.assertTrue(snakesAfterInsert.size() == snakesBeforeInsert.size() + 1);
/*
        SnakeEntity newSnake = newSnakes.get(0);

        Assertions.assertEquals(name,newSnake.getName());
        Assertions.assertEquals(expPoint,newSnake.getExpPoint());
        Assertions.assertEquals(info,newSnake.getInfo());
        Assertions.assertEquals(user.get().getId(),newSnake.getIdUser());
        Assertions.assertEquals(snakeClass.get().getId(),newSnake.getSnakeClass().getId());
*/

    }
}
