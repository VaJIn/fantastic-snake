package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.ConnectionPool;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameModeEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.cached.CacheProxyGameEntity;

import java.sql.*;
import java.util.*;


public class GameDAOImpl implements GameDAO {

    private DAOFactory daoFactory;

    public GameDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;

    }

    private List<GameEntity> getGameByCondition(String condition, int sortBy) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM Game ");
        if (condition != null && !condition.equals("")) {
            queryBuilder.append("WHERE ").append(condition);
        }
        queryBuilder.append(" ").append(sortBy(sortBy)).append(";");

        String query = queryBuilder.toString();

        ResultSet rs = null;
        List<GameEntity> games = new ArrayList<GameEntity>();
        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

            rs = statement.executeQuery(query);
            while (rs.next()) {
                try {
//                    GameEntity g = new GameEntity();
//                    g.setId(rs.getInt("id"));
//                    g.setStartTime(rs.getTimestamp("startTime"));
//                    g.setEndTime(rs.getTimestamp("endTime"));
//                    int gamemodeId = rs.getInt("idGameMode");

                    games.add(resultSetToGameEntity(rs));
                } catch (SQLException e) {
                    //Means that a row of the sql table has incorrect value(s). Not returning null nor returning empty list so that the other
                    //potentially read rows are correctly retrieved.
                    e.printStackTrace();
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return games;
    }

    @Override
    public void addGame(GameEntity gameEntity) throws SQLException {
        String updateGame = "INSERT INTO Game (startTime, endTime, idGameMode) " +
                "VALUES ('" + gameEntity.getStartTime() + "', '" + gameEntity.getEndTime() + "', " + gameEntity.getGameMode().getId() + ");";

        Connection connection = ConnectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateGame, Statement.RETURN_GENERATED_KEYS);
        statement.addBatch(updateGame);

        ResultSet keys = statement.getGeneratedKeys();

        int idGame = -1;
        if (keys.next()) {
            idGame = keys.getInt(0);
            gameEntity.setId(idGame);
        }


        for (GameParticipationEntity gp : gameEntity.getGameParticipationEntities()) {

            String updateGameParticipation = "INSERT INTO GameParticipation " +
                    "VALUES (" + idGame + ", " + gp.getIdUser() + ", " + gp.getScore() + ", " + gp.getKillCount() + ", " + gp.getDeathCount() + ");";

            statement.addBatch(updateGameParticipation);

        }

        statement.executeBatch();

        connection.close();
    }

    @Override
    public Optional<GameEntity> getGame(int id) {

        String condition = "id = " + id;

        Collection<GameEntity> result = getGameByCondition(condition, -1);


        Iterator<GameEntity> it = result.iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        }
        return Optional.empty();
    }

    //Si earliest ou latest est null, alors on ignore la condition
    @Override
    public List<GameEntity> getGameByDate(Timestamp earliest, Timestamp latest, int sortBy) {
        String condition = "";
        if (earliest != null) {
            condition = "WHERE (startime > '" + earliest + "')";
        }
        if (latest != null) {
            if (condition.equals("")) {
                condition = "WHERE";
            } else {
                condition += " AND";
            }
            condition += " (startime < '" + latest + "')";
        }
        return getGameByCondition(condition, sortBy);
    }

    @Override
    public List<GameEntity> getGameByGameMode(GameModeEntity gameModeEntity, int sortBy) {
        String condition = "idGameMode=" + gameModeEntity.getId() + " ";

        return getGameByCondition(condition, sortBy);
    }

    private GameEntity resultSetToGameEntity(ResultSet rs) throws SQLException {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+0"));

        GameEntity entity = new CacheProxyGameEntity(this.daoFactory);

        int id = rs.getInt("id");
        entity.setId(id);

        Timestamp startTime = rs.getTimestamp("startTime", calendar);
        entity.setStartTime(startTime);

        Timestamp endTime = rs.getTimestamp("endTime", calendar);
        entity.setEndTime(endTime);

        int idGameMode = rs.getInt("idGameMode");
        entity.setGameModeId(idGameMode);

        return entity;
    }

    private String sortBy(int sortBy) {
        switch (sortBy) {
            case DAOFactory.SORT_BY_EARLIEST_DATE:
                return "ORDER BY startTime";
            case DAOFactory.SORT_BY_LATEST_DATE:
                return "ORDER BY startTime DESC";
            case DAOFactory.SORT_BY_SCORE_ASC:
                return "ORDER BY score";
            case DAOFactory.SORT_BY_SCORE_DESC:
                return "ORDER BY score DESC";
        }
        return "";
    }
}
