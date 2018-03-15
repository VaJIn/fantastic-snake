package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.ConnectionPool;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.UserDAO;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.database.entities.cached.CacheProxyUserEntity;

import java.sql.*;
import java.util.*;

public class UserDAOImpl implements UserDAO {

    private DAOFactory daoFactory;

    protected UserDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private Collection<UserEntity> getUserByCondition(String condition) {
        String query = "SELECT * " +
                " FROM User " +
                " WHERE " + condition + ";";

        Set<UserEntity> out = new HashSet<>();

        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                try {
                    UserEntity user = resultSetToUserEntity(rs);
                    out.add(user);
                } catch (SQLException e) {
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                    e.printStackTrace();
                }

            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null; //Only if the query is not well formed
        }

        return out;
    }

    @Override
    public int addUser(UserEntity userEntity) throws SQLException {
        String updateUser = "INSERT INTO User (alias, email, accountName, password) " +
                "VALUES ('" + userEntity.getAlias() + "', '" + userEntity.getEmail() + "', '" + userEntity.getAccountName() + "', '" + userEntity.getPassword() + "');";

        Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateUser, Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();

        int idUser = -1;
        ResultSet generatedKey = statement.getGeneratedKeys();
        if (generatedKey.next()) {
            idUser = generatedKey.getInt(1);
            userEntity.setId(idUser);
        }

        connection.close();

        return idUser;
    }

    @Override
    public Optional<UserEntity> getUser(int id) {

        String condition = "id = " + id;

        Iterator<UserEntity> it = getUserByCondition(condition).iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> getUser(String accountName, String hash) {
        String condition = "accountName='" + accountName + "' AND password='" + hash + "'";
        Iterator<UserEntity> it = getUserByCondition(condition).iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<UserEntity> getUserByAlias(String alias) {
        String condition = "alias='" + alias + "'";

        return getUserByCondition(condition);
    }

    @Override
    public Optional<UserEntity> getUserByAccountName(String accountName) {
        String condition = "accountName='" + accountName + "'";

        Iterator<UserEntity> it = getUserByCondition(condition).iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns a UserEntity from a ResultSet of the table User
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private UserEntity resultSetToUserEntity(ResultSet rs) throws SQLException {

        UserEntity entity = new CacheProxyUserEntity(this.daoFactory);

        int id = rs.getInt("id");
        entity.setId(id);

        String alias = rs.getString("alias");
        entity.setAlias(alias);

        String email = rs.getString("email");
        entity.setEmail(email);

        String accountName = rs.getString("accountName");
        entity.setAccountName(accountName);

        String password = rs.getString("password");
        entity.setPassword(password);

        return entity;
    }
}
