import com.google.common.collect.Sets;
import fr.vajin.snakerpg.database.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

public class EntitiesTest {

    @Test
    void testUserSnake() {
        UserEntity user = new UserEntity(1, "alias1", "user1@domain.fr", "user1", "123456");
        SnakeEntity snakeEntity = new SnakeEntity();
        snakeEntity.setName("snake1");
        snakeEntity.setId(1);

        SnakeEntity snakeEntity1 = new SnakeEntity();

        user.setSnakes(Sets.newHashSet(snakeEntity1));

        snakeEntity.setUser(user);

        Assertions.assertTrue(user.getSnakes().contains(snakeEntity1));
        Assertions.assertTrue(user.getSnakes().contains(snakeEntity));
        Assertions.assertTrue(snakeEntity.getUser() == user);
        Assertions.assertTrue(snakeEntity1.getUser() == user);
    }

    @Test
    void testGameParticipation() {
        GameEntity game = new GameEntity();
        game.setId(1);
        game.setStartTime(Timestamp.from(Instant.now()));
        game.setEndTime(Timestamp.from(Instant.now().plusSeconds(125)));
        game.setGameMode(new GameModeEntity());

        GameParticipationEntity gameParticipationEntity = new GameParticipationEntity();
        GameParticipationEntity gameParticipationEntity1 = new GameParticipationEntity();

        game.setGameParticipationEntities(Sets.newHashSet(gameParticipationEntity1, gameParticipationEntity));

        gameParticipationEntity.setGame(game);

        Assertions.assertTrue(game.getGameParticipationEntities().contains(gameParticipationEntity));
        Assertions.assertTrue(game.getGameParticipationEntities().contains(gameParticipationEntity1));

        Assertions.assertTrue(gameParticipationEntity.getGame() == game);
        Assertions.assertTrue(gameParticipationEntity1.getGame() == game);
    }
}
