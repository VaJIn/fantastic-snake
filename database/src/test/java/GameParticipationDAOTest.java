import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class GameParticipationDAOTest {

    GameParticipationDAO gameParticipationDAO = DAOFactoryProvider.getDAOFactory().getGameParticipationDAO();

    @Test
    @DisplayName("Test GameParticipationDAO getGameResultsByUser")
    void testGetGameResultsByUser() {

        Assertions.assertNotNull(gameParticipationDAO.getGameResultsByUser(1, 0, new Timestamp(0), new Timestamp(0)));
    }

    @Test
    @DisplayName("Test GameParticipationDAO getGameResultsByGame")
    void testGetGameResultsByGame() {

        Collection<GameParticipationEntity> results = gameParticipationDAO.getGameResultsByGame(-1, 0);
        Assertions.assertNotNull(results);
        Assertions.assertEquals(0, results.size());
        Assertions.assertNotNull(gameParticipationDAO.getGameResultsByGame(1, 0));

        results = gameParticipationDAO.getGameResultsByGame(2, DAOFactory.SORT_BY_SCORE_DESC);

        Assertions.assertNotNull(results);
        Assertions.assertFalse(results.isEmpty());

        /*Check is ordered by score desc, and that every GameParticipation has the same GameEntity object*/
        Iterator<GameParticipationEntity> it = results.iterator();
        GameParticipationEntity other = it.next();
        GameParticipationEntity current = other;
        GameEntity game = other.getGame();
        Assertions.assertNotNull(game);
        while (it.hasNext()) {
            Assertions.assertEquals(2, current.getIdGame());
            current = it.next();
            Assertions.assertTrue(other.getScore() >= current.getScore());
            other = current;
        }
    }

    @Test
    @DisplayName("Test GameParticipation getGameParticipationByIds")
    void testGetGameResultsByIds() {
        Optional<GameParticipationEntity> gp = gameParticipationDAO.getGameParticipationByIds(-1, -1, 0);
        Assertions.assertFalse(gp.isPresent());

        gp = gameParticipationDAO.getGameParticipationByIds(1, 1, 0);
        Assertions.assertTrue(gp.isPresent());

    }
}
