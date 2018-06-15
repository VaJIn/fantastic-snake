package fr.vajin.snakerpg.servlet;

import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.form.LoginFormLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginServlet extends HttpServlet {

    public static final String VIEW = "/WEB-INF/loginPage.jsp";
    private Map<String, String> idsMap;

    public LoginServlet() {
        this.idsMap = new HashMap<>();
        idsMap.put("accountName", LoginFormLogic.CHAMP_ACCOUNT_NAME);
        idsMap.put("password", LoginFormLogic.CHAMP_PASSWORD);
        idsMap.put("form", LoginFormLogic.FORM_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("id", idsMap);

        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LoginFormLogic formLogic = new LoginFormLogic();

        Optional<UserEntity> userEntity = formLogic.logInUser(request);

        if (userEntity.isPresent()) {
            HttpSession session = request.getSession();

            session.setAttribute("user", userEntity.get());

            response.sendRedirect("/home");
        } else {
            if (formLogic.getErrors().isEmpty()) {
                formLogic.getErrors().put("login", "Wrong account name or password");
            }

            request.setAttribute("oldValues", formLogic.getValues());
            request.setAttribute("error", formLogic.getErrors());
            request.setAttribute("id", idsMap);
            this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
        }
    }
}
