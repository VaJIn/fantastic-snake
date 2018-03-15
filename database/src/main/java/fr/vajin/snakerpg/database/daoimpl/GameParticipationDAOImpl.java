package fr.vajin.snakerpg.database.daoimpl;

import fr.vajin.snakerpg.database.ConnectionPool;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.cached.CacheProxyGameParticipationEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class GameParticipationDAOImpl implements GameParticipationDAO {

    private DAOFactory daoFactory;


    public GameParticipationDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private String getTimeCondition(Timestamp earliest, Timestamp latest) {
        String timeCondition = "";
        if (earliest != null) {
            timeCondition += "WHERE ( startTime >'" + earliest + "')";
            if (latest != null) {
                timeCondition += " AND ";
            }
        }
        if (latest != null) {
            if (earliest == null) {
                timeCondition += "WHERE ";
            }
            timeCondition += "( startTime < '" + latest + "')";
        }
        return timeCondition;
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest, int startIndex, int count) {

        String timeCondition = getTimeCondition(earliest, latest);

        String query = "SELECT gp.idGame idGame, gp.idUser, gp.score, gp.killCount, gp.deathCount, g.id, g.startTime, g.endTime, g.idGameMode "
                + "FROM GameParticipation gp "
                + "JOIN (SELECT * FROM Game " + timeCondition + ") as g "
                + "ON gp.idGame = g.id AND gp.idUser = " + userId + " "
                + sortBy(sortBy);

        if (startIndex > -1) {
            query += "LIMIT " + startIndex + "," + count;
        }

        query += ";";

        return gameParticipationQuery(query);
    }


    @Override
    public List<GameParticipationEntity> getGameResultsByUser(int userId, int sortBy, Timestamp earliest, Timestamp latest) {
        return getGameResultsByUser(userId, sortBy, earliest, latest, -1, 0);
    }

    @Override
    public List<GameParticipationEntity> getGameResultsByGame(int gameId, int sortBy) {
        String query = "SELECT gp.idGame idGame, gp.idUser idUser, gp.score score, gp.killCount killCount, gp.deathCount deathCount\n"
                + "FROM GameParticipation gp "
                + "JOIN Game g "
                + "ON gp.idGame  = g.id \n"
                + "WHERE gp.idGame = " + gameId + " "
                + sortBy(sortBy) + ";";


        return gameParticipationQuery(query);
    }

    @Override
    public Optional<GameParticipationEntity> getGameParticipationByIds(int gameId, int userId, int sortBy) {
        String query = "SELECT gp.idGame idGame, gp.idUser idUser, gp.score score, gp.killCount killCount, gp.deathCount deathCount\n"
                + "FROM GameParticipation gp "
                + "JOIN Game g ON gp.idGame  = g.id \n"
                + "WHERE idUser=" + userId + " AND idGame=" + gameId + " "
                + sortBy(sortBy) + ";";

        Iterator<GameParticipationEntity> it = gameParticipationQuery(query).iterator();
        if (it.hasNext()) {
            return Optional.of(it.next());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<GameParticipationEntity> getGameParticipation(Timestamp earliest, Timestamp latest, int sortBy, int startIndex, int count) {
        String condition = getTimeCondition(earliest, latest);

        String query = "SELECT gp.idGame idGame, gp.idUser idUser, gp.score score, gp.killCount killCount, gp.deathCount deathCount\n"
                + "FROM GameParticipation gp "
                + "JOIN Game g ON gp.idGame  = g.id \n"
                + condition
                + sortBy(sortBy);

        if (startIndex > -1) {
            query += "LIMIT " + startIndex + "," + count;
        }

        query += ";";
        return gameParticipationQuery(query);

    }

    @Override
    public List<GameParticipationEntity> getGameParticipation(Timestamp earliest, Timestamp latest, int sortBy) {

        return getGameParticipation(earliest, latest, sortBy, -1, 0);

    }

    private List<GameParticipationEntity> gameParticipationQuery(String query) {
        List<GameParticipationEntity> gameResults = new ArrayList<>();
        try {
            Connection connection = ConnectionPool.getConnection();
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                try {
                    gameResults.add(resultSetToGameParticipationEntity(rs));
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

        return gameResults;
    }

    private GameParticipationEntity resultSetToGameParticipationEntity(ResultSet rs) throws SQLException {

        int idGame = rs.getInt("idGame");
        int idUser = rs.getInt("idUser");
        int score = rs.getInt("score");
        int killCount = rs.getInt("killCount");
        int deathCount = rs.getInt("deathCount");

        GameParticipationEntity entity = new CacheProxyGameParticipationEntity(this.daoFactory);

        entity.setIdGame(idGame);
        entity.setIdUser(idUser);
        entity.setDeathCount(deathCount);
        entity.setKillCount(killCount);
        entity.setScore(score);

        return entity;
    }

    private String sortBy(int sortBy) {
        switch (sortBy) {
            case DAOFactory.SORT_BY_EARLIEST_DATE:
                return "ORDER BY g.startTime ";
            case DAOFactory.SORT_BY_LATEST_DATE:
                return "ORDER BY g.startTime DESC ";
            case DAOFactory.SORT_BY_SCORE_ASC:
                return "ORDER BY score ";
            case DAOFactory.SORT_BY_SCORE_DESC:
                return "ORDER BY score DESC ";
        }
        return " ";
    }
}
