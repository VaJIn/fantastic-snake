package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Facade
 * Allow access to the useful information in the database from outside of the database package without knowledges of the framework used.
 */
public interface DataBaseAccess {

    /**
     * Return the user with the given id, or null if none is found
     *
     * @param id the id of the user to retrieve
     * @return the UserEntity of the user matching the given id, or null if none is found
     */
    UserEntity getUser(int id);

    /**
     * Return all user matching the given alias.
     *
     * @param alias
     * @return a collection of all users matching the given pseudo
     */
    Collection<UserEntity> getUserByAlias(String alias);

    UserEntity getUserByAccountName(String accountName);

    UserEntity getUser(String accountName, String hash);

    /**
     * Return the Snake matching the given id
     *
     * @param id the id of the snake to retrieve
     * @return the SnakeEntity of the snake matching the given id
     */
    SnakeEntity getSnakeById(int id);

    /**
     * Return all snake of the given user
     *
     * @param userEntity
     * @return a collection of all snake belonging to the given user.
     */
    Collection<SnakeEntity> getSnakeByUser(UserEntity userEntity);

    /**
     * Return all snake for the user with the given id
     *
     * @param userId the id of the user.
     * @return a collection of all snake belonging to the user matching the given id. The collection is null is the user does not exists.
     */
    Collection<SnakeEntity> getSnakeByUser(int userId);

    /**
     * Returns the snake class matching the given ID
     *
     * @param snakeClassId
     * @return
     */
    SnakeClassEntity getSnakeClassById(int snakeClassId);

    int SORT_BY_EARLIEST_DATE = 0;
    int SORT_BY_LATEST_DATE = 1;
    int SORT_BY_SCORE_ASC = 2;
    int SORT_BY_SCORE_DESC = 3;

    List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest);

    List<GameParticipationEntity> getGameResultsByUser(UserEntity userEntity, int sortBy, Timestamp earliest, Timestamp latest);

    List<GameParticipationEntity> getGameResultsByGame(int gameid, int sortBy);

    Collection<GameParticipationEntity> getGameResultsByGame(GameEntity gameEntity, int sortBy);

    GameEntity getGame(int id);

    List<GameEntity> getGameByDate(Timestamp earliest, Timestamp latest, int sortBy);

    List<GameEntity> getGameByGamemode(GameModeEntity gameModeEntity, int sortBy);

    GameModeEntity getGameMode(int id);

    GameModeEntity getGameMode(String name);

    Collection<GameModeEntity> getAllGameModes();


}
