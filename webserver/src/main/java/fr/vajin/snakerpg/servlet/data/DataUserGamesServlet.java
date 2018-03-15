package fr.vajin.snakerpg.servlet.data;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.DAOFactory;
import fr.vajin.snakerpg.database.GameParticipationDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "DataUserGamesServlet")
public class DataUserGamesServlet extends HttpServlet {

    static final String USER_ID_PARAMETER = "userId";
    static final String SORT_BY_PARAMETER = "sort";
    static final String START_INDEX_PARAMETER = "fromIndex";
    static final String COUNT_PARAMETER = "count";

    static final String SORT_BY_SCORE_DESC = "score_best";
    static final String SORT_BY_SCORE_ASC = "score_worst";
    static final String SORT_BY_EARLIEST = "most_recent";
    static final String SORT_BY_LATEST = "oldest";

    static final int DEFAULT_COUNT = 10;

    GameParticipationDAO gameParticipationDAO = FactoryProvider.getDAOFactory().getGameParticipationDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int userId;
        try {
            userId = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String sortByStr = request.getParameter(SORT_BY_PARAMETER);
        int sortBy = DAOFactory.SORT_BY_LATEST_DATE;
        if (sortByStr != null) {
            switch (sortByStr) {
                case SORT_BY_SCORE_DESC:
                    sortBy = DAOFactory.SORT_BY_SCORE_DESC;
                    break;
                case SORT_BY_SCORE_ASC:
                    sortBy = DAOFactory.SORT_BY_SCORE_ASC;
                    break;
                case SORT_BY_EARLIEST:
                    sortBy = DAOFactory.SORT_BY_EARLIEST_DATE;
                    break;
                case SORT_BY_LATEST:
                    sortBy = DAOFactory.SORT_BY_LATEST_DATE;
                    break;
                default:
                    sortBy = -1;
            }
        }

        String parameterStartIndex = request.getParameter(START_INDEX_PARAMETER);
        int startIndex = 0;
        if (parameterStartIndex != null) {
            try {
                startIndex = Integer.parseInt(parameterStartIndex);
            } catch (NumberFormatException e) {
                //Ignore exception
            }
        }

        String parameterCount = request.getParameter(COUNT_PARAMETER);
        int count = DEFAULT_COUNT;
        if (parameterCount != null) {
            try {
                count = Integer.parseInt(parameterCount);
            } catch (NumberFormatException e) {
                //Ignore exception
            }
        }

        Collection<GameParticipationEntity> gameParticipationEntities = gameParticipationDAO.getGameResultsByUser(userId, sortBy, null, null, startIndex, count);

        Collection<GameEntity> gameEntities = Lists.newArrayListWithCapacity(gameParticipationEntities.size());

        for (GameParticipationEntity gameParticipationEntity : gameParticipationEntities) {
            gameEntities.add(gameParticipationEntity.getGame());
        }

        Gson gson = new Gson();

        String resultJSON = gson.toJson(gameEntities);

        response.setContentType("application/json");
        response.getWriter().write(resultJSON);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
