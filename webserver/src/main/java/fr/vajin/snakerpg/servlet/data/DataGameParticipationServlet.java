package fr.vajin.snakerpg.servlet.data;

import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.GameParticipationEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class DataGameParticipationServlet extends HttpServlet {
    private static final String GAME_ID_PARAMETER = "gameId";
    private static final String USER_ID_PARAMETER = "userId";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer idGame = null;
        Integer idUser = null;
        String paramSortBy;
        try {
            idGame = Integer.parseInt(request.getParameter(GAME_ID_PARAMETER));
            idUser = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Optional<GameParticipationEntity> gameParticipationEntityOptional =
                FactoryProvider
                        .getDAOFactory()
                        .getGameParticipationDAO()
                        .getGameParticipationByIds(idGame, idUser, 0);

        response.setContentType("application/json");

        if (gameParticipationEntityOptional.isPresent()) {
            Gson gson = new Gson();

            String gameParticipationJSON = gson.toJson(gameParticipationEntityOptional.get());

            response.getWriter().write(gameParticipationJSON);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
