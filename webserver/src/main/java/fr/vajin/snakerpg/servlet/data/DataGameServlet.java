package fr.vajin.snakerpg.servlet.data;

import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.GameEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class DataGameServlet extends HttpServlet {

    static final String GAME_ID_PARAMETER = "gameId";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id;
        try {
            id = Integer.parseInt(request.getParameter(GAME_ID_PARAMETER));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Optional<GameEntity> gameEntityOptional = FactoryProvider.getDAOFactory().getGameDAO().getGame(id);


        if (gameEntityOptional.isPresent()) {

            GameEntity gameEntity = gameEntityOptional.get();
            gameEntity.getGameParticipationEntities();

            Gson gson = new Gson();

            String gameJSON = gson.toJson(gameEntityOptional.get());

            response.setContentType("application/json");
            response.getWriter().write(gameJSON);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
