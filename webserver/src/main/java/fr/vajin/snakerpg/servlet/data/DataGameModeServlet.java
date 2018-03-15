package fr.vajin.snakerpg.servlet.data;

import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.GameModeEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DataGameModeServlet")
public class DataGameModeServlet extends HttpServlet {

    static final String GAME_MODE_ID_PARAMETER = "gameModeId";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id;

        try {
            id = Integer.parseInt(request.getParameter(GAME_MODE_ID_PARAMETER));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Optional<GameModeEntity> gameMode = FactoryProvider.getDAOFactory().getGameModeDAO().getGameMode(id);

        if (gameMode.isPresent()) {

            Gson gson = new Gson();

            String gameModeJSON = gson.toJson(gameMode.get());

            response.setContentType("application/json");
            response.getWriter().write(gameModeJSON);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
