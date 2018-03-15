package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.ConnectionPool;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.entities.SnakeClassEntity;

import java.sql.*;
import java.util.*;

public class SnakeClassDAOImpl implements SnakeClassDAO {

    private DAOFactory daoFactory;

    public SnakeClassDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;


    }

    @Override
    public Optional<SnakeClassEntity> getSnakeClassById(int snakeClassId) {
        String query = "SELECT * " +
                "FROM SnakeClass " +
                "WHERE id=" + snakeClassId;

        query += ";";
        SnakeClassEntity out = null;

        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                out = resultSetToSnakeClassEntity(rs);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            out = null;
        }

        return Optional.ofNullable(out);
    }

    @Override
    public Collection<SnakeClassEntity> getAllSnakeClasses() {
        String query = "SELECT * " +
                "FROM SnakeClass;";

        Collection<SnakeClassEntity> out = new ArrayList<>();


        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                try {
                    out.add(resultSetToSnakeClassEntity(rs));
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

    private SnakeClassEntity resultSetToSnakeClassEntity(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String name = rs.getString("name");

        return new SnakeClassEntity(id, name);

    }
}
