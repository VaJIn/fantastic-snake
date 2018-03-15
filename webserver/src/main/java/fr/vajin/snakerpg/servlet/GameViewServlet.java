package fr.vajin.snakerpg.servlet;


import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.GameDAO;
import fr.vajin.snakerpg.database.entities.GameEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "GameInfoServlet")
public class GameViewServlet extends HttpServlet {

    public static final String VIEW = "/WEB-INF/views/gameview.jsp";
    GameDAO gameDAO = FactoryProvider.getDAOFactory().getGameDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        int gameId;
        //TODO handle exception
        try {
            gameId = Integer.parseInt(uri.replace("/game/", ""));
        } catch (NumberFormatException e) {
            gameId = -111;
            e.printStackTrace(response.getWriter());
        }

        Optional<GameEntity> gameEntityOptional = gameDAO.getGame(gameId);

        if (gameEntityOptional.isPresent()) {
            request.setAttribute("gameEntity", gameEntityOptional.get());

            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No game for id " + gameId);
        }
    }

}
