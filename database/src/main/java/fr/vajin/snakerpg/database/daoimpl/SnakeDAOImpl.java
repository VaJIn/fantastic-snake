package fr.vajin.snakerpg.database.daoimpl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import fr.vajin.snakerpg.database.ConnectionPool;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.SnakeClassDAO;
import fr.vajin.snakerpg.database.SnakeDAO;
import fr.vajin.snakerpg.database.entities.SnakeClassEntity;
import fr.vajin.snakerpg.database.entities.SnakeEntity;
import fr.vajin.snakerpg.database.entities.cached.CacheProxySnakeEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class SnakeDAOImpl implements SnakeDAO {

    private DAOFactory daoFactory;

    public SnakeDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addSnake(SnakeEntity snakeEntity) throws SQLException {
        String updateSnake = "INSERT INTO Snake (userID, name, exp, info, idSnakeClass) " +
                "VALUES (" + snakeEntity.getUser().getId() + ", '" + snakeEntity.getName() + "', " + snakeEntity.getExpPoint() +
                ", " + snakeEntity.getInfo() + ", " + snakeEntity.getSnakeClass().getId() + ");";

        Connection connection = ConnectionPool.getConnection();
        Statement statement = connection.createStatement();
        statement.addBatch(updateSnake);
        statement.executeBatch();
        connection.close();
    }

    private Collection<SnakeEntity> getSnakeByCondition(String condition) {
        String query = "SELECT *" +
                " FROM Snake " +
                " WHERE " + condition + ";";

        Collection<SnakeEntity> out = new HashSet<>();

        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                try {
                    out.add(resultSetToSnakeEntity(rs));
                } catch (SQLException e) {
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                    e.printStackTrace();
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        SnakeClassDAO snakeClassDAO = daoFactory.getSnakeClassDAO();
        //Retrieve SnakeClass
        LoadingCache<Integer, SnakeClassEntity> cache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, SnakeClassEntity>() {
            @Override
            public SnakeClassEntity load(Integer key) throws Exception {
                Optional<SnakeClassEntity> optional = snakeClassDAO.getSnakeClassById(key);
                return optional.orElse(new SnakeClassEntity());
            }
        });
        for (SnakeEntity snakeEntity : out) {
            try {
                snakeEntity.setSnakeClass(cache.get(snakeEntity.getSnakeClassId()));
            } catch (ExecutionException e) {
                //TODO log
            }
        }

        return out;
    }

    @Override
    public Optional<SnakeEntity> getSnakeById(int id) {
        String condition = " id = " + id;

        Collection<SnakeEntity> results = getSnakeByCondition(condition);

        if (results != null) {
            Iterator<SnakeEntity> it = results.iterator();
            if (it.hasNext()) {
                return Optional.of(it.next());
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<SnakeEntity> getSnakeByUser(int userId) {
        String condition = "userId= " + userId;

        return getSnakeByCondition(condition);
    }

    private SnakeEntity resultSetToSnakeEntity(ResultSet rs) throws SQLException {

        SnakeEntity entity = new CacheProxySnakeEntity(this.daoFactory);

        int id = rs.getInt("id");
        entity.setId(id);

        int userId = rs.getInt("userId");
        entity.setUserId(userId);

        String name = rs.getString("name");
        entity.setName(name);

        int exp = rs.getInt("exp");
        entity.setExpPoint(exp);

        byte[] info = rs.getBytes("info");
        entity.setInfo(info);

        int idSnakeClass = rs.getInt("idSnakeClass");
        entity.setSnakeClassId(idSnakeClass);

        return entity;
    }
}
