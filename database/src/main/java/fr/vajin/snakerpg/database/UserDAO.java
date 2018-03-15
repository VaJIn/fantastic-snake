package fr.vajin.snakerpg.database;

import fr.vajin.snakerpg.database.entities.UserEntity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface UserDAO {

    /**
     * Insert the given user entity in the database. The propriety id in the userEntity is set to the attributed id by the database.
     *
     * @param userEntity the user to insert in the database. The id property is modified to the attributed id.
     * @return the id of the inserted user.
     * @throws SQLException
     */
    int addUser(UserEntity userEntity) throws SQLException;

    Optional<UserEntity> getUser(int id);

    Optional<UserEntity> getUser(String accountName, String hash);

    Collection<UserEntity> getUserByAlias(String alias);

    Optional<UserEntity> getUserByAccountName(String accountName);

}
