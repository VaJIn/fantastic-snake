package fr.vajin.snakerpg.servlet.data;

import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.SnakeEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class DataSnakeServlet extends HttpServlet {
    static final String SNAKE_ID_PARAMETER = "snakeId";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id;
        try {
            id = Integer.parseInt(request.getParameter(SNAKE_ID_PARAMETER));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Optional<SnakeEntity> snake = FactoryProvider.getDAOFactory().getSnakeDAO().getSnakeById(id);

        if (snake.isPresent()) {
            Gson gson = new Gson();

            String snakeJSON = gson.toJson(snake.get());

            response.setContentType("application/json");
            response.getWriter().write(snakeJSON);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
