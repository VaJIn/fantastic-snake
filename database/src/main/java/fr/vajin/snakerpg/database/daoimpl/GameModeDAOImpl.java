package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.ConnectionPool;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameModeDAO;
import fr.vajin.snakerpg.database.entities.GameModeEntity;

import java.sql.*;
import java.util.*;

public class GameModeDAOImpl implements GameModeDAO {

    private DAOFactory daoFactory;

    public GameModeDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;

    }

    @Override
    public Collection<GameModeEntity> getAllGameMode() {
        String query = "SELECT * " +
                "FROM GameMode";

        query += ";";
        Collection<GameModeEntity> out = new ArrayList<>();


        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                try {
                    out.add(resultSetToGameModeEntity(rs));
                } catch (SQLException e) {
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                }

            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return out;
    }

    @Override
    public Optional<GameModeEntity> getGameMode(int id) {
        String query = "SELECT * " +
                "FROM GameMode " +
                "WHERE id=" + id;

        query += ";";
        GameModeEntity out = null;

        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                out = resultSetToGameModeEntity(rs);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(out);
    }

    @Override
    public Optional<GameModeEntity> getGameMode(String name) {
        String query = "SELECT * " +
                "FROM GameMode " +
                "WHERE name='" + name + "'";

        query += ";";

        GameModeEntity out = null;

        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {

                out = resultSetToGameModeEntity(rs);
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            //out=null;

        }

        return Optional.ofNullable(out);
    }

    private GameModeEntity resultSetToGameModeEntity(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String name = rs.getString("name");
        int minPlayer = rs.getInt("minPlayer");
        int maxPlayer = rs.getInt("maxPlayer");

        return new GameModeEntity(id, name, minPlayer, maxPlayer);
    }
}
