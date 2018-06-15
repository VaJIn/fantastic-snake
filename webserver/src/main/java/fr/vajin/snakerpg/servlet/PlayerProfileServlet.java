package fr.vajin.snakerpg.servlet;

import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class PlayerProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public static final String PARAMETER_USER_ID = "userId";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userIdStr = request.getParameter("userId");

        Optional<UserEntity> user;

        int userId;

        if (userIdStr != null) {
            try {
                userId = Integer.parseInt(userIdStr);

                user = FactoryProvider.getDAOFactory().getUserDAO().getUser(userId);

                if (user.isPresent()) {
                    request.setAttribute("user", user.get());
                    this.getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorCode", 404);
                    request.setAttribute("errorMsg", "The requested user (" + userIdStr + ") does not exist");
                    this.getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorCode", HttpServletResponse.SC_BAD_REQUEST);
                request.setAttribute("errorMsg", "Invalid parameter userId (must be a integer)");
                this.getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("errorMsg", "The request need a userId parameter");
            this.getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(request, response);
        }
    }
}
