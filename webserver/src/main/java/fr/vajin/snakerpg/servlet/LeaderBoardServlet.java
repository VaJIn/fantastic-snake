package fr.vajin.snakerpg.servlet;


import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@WebServlet(name = "LeaderBoardServlet")
public class LeaderBoardServlet extends HttpServlet {

    private static final String PERIOD_PARAMETER = "period";
    private static final String PERIOD_TODAY = "today";
    private static final String PERIDO_THIS_WEEK = "thisWeek";
    private static final String PERIOD_THIS_MONTH = "thisMonth";
    private static final String PERIOD_ALL_TIME = "all";
    private static final String PARAMETER_FROM = "from";
    private static final String PARAMETER_TO = "to";
    private GameParticipationDAO gameParticipationDAO = FactoryProvider.getDAOFactory().getGameParticipationDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
                case PERIDO_THIS_WEEK:
                    earliest = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.WEEKS));
                    break;
                case PERIOD_THIS_MONTH:
                    earliest = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MONTHS));
                    break;
            }
        }

        StringBuilder builder = new StringBuilder("");
        if (earliest != null) {
            builder.append("from ").append(earliest.toLocalDateTime().toString());
            if (latest != null) {
                builder.append(" to ").append(latest.toLocalDateTime().toString());
            }
        } else if (latest != null) {
            builder.append("up to ").append(latest.toLocalDateTime().toString());
        } else {
            builder.append("all time");
        }

        timeline = builder.toString();


        Collection<GameParticipationEntity> gameParticipationEntities = gameParticipationDAO.getGameParticipation(earliest, latest, DAOFactory.SORT_BY_SCORE_DESC);

        request.setAttribute("period", timeline);
        request.setAttribute("listGameParticipation", gameParticipationEntities);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/leaderboardView.jsp").include(request, response);
    }
}
