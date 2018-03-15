package fr.vajin.snakerpg.servlet.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.servlet.data.serializer.PublicUserEntitySerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DataLeaderBoardServlet extends HttpServlet {

    private static final String PERIOD_PARAMETER = "period";
    private static final String PERIOD_TODAY = "today";
    private static final String PERIoD_THIS_WEEK = "thisWeek";
    private static final String PERIOD_THIS_MONTH = "thisMonth";
    private static final String PERIOD_ALL_TIME = "all";
    private static final String PARAMETER_FROM = "from";
    private static final String PARAMETER_TO = "to";
    private GameParticipationDAO gameParticipationDAO = FactoryProvider.getDAOFactory().getGameParticipationDAO();
    private Gson gson;

    public DataLeaderBoardServlet() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(UserEntity.class, new PublicUserEntitySerializer());

        this.gson = gsonBuilder.create();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String start = request.getParameter(PARAMETER_FROM);
        String end = request.getParameter(PARAMETER_TO);

        Timestamp earliest = null;
        Timestamp latest = null;

        String timeline;

        if (start != null) {
            earliest = Timestamp.valueOf(start);
        }
        if (end != null) {
            latest = Timestamp.valueOf(end);
        }

        if (start == null && end == null) {
            String period = request.getParameter(PERIOD_PARAMETER);
            if (period == null) {
                period = PERIOD_ALL_TIME;
            }


            switch (period) {
                case PERIOD_TODAY:
                    earliest = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.DAYS));
                    break;
                case PERIoD_THIS_WEEK:
                    earliest = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.WEEKS));
                    break;
                case PERIOD_THIS_MONTH:
                    earliest = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MONTHS));
                    break;
            }
        }

        List<GameParticipationEntity> gameParticipationEntities = gameParticipationDAO.getGameParticipation(earliest, latest, DAOFactory.SORT_BY_SCORE_DESC);

        LeaderBoardData leaderBoardData = new LeaderBoardData(gameParticipationEntities);

        String jsonString = gson.toJson(leaderBoardData);

        response.setContentType("application/json");
        response.getWriter().write(jsonString);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
