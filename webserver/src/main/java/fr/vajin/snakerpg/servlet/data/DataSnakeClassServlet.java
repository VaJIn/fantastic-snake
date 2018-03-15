package fr.vajin.snakerpg.servlet.data;

import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.SnakeClassEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DataSnakeClassServlet")
public class DataSnakeClassServlet extends HttpServlet {

    static final String SNAKE_CLASS_ID_PARAMETER = "snakeClassId";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id;

        try {
            id = Integer.parseInt(request.getParameter(SNAKE_CLASS_ID_PARAMETER));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Optional<SnakeClassEntity> snakeClass = FactoryProvider.getDAOFactory().getSnakeClassDAO().getSnakeClassById(id);

        if (snakeClass.isPresent()) {
            Gson gson = new Gson();

            String snakeClassJSON = gson.toJson(snakeClass.get());

            response.setContentType("application/json");
            response.getWriter().write(snakeClassJSON);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
