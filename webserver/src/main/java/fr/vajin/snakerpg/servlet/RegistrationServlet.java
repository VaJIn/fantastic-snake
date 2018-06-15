package fr.vajin.snakerpg.servlet;

import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.form.RegistrationFormLogic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {

    private final static String VIEW = "/WEB-INF/registrationPage.jsp";

    private Map<String, String> idsMap;

    public RegistrationServlet() {
        this.idsMap = new HashMap<>();
        idsMap.put("formName", RegistrationFormLogic.FORM_NAME);
        idsMap.put("email", RegistrationFormLogic.CHAMP_EMAIL);
        idsMap.put("alias", RegistrationFormLogic.CHAMP_ALIAS);
        idsMap.put("accountName", RegistrationFormLogic.CHAMP_ACCOUNT_NAME);
        idsMap.put("password", RegistrationFormLogic.CHAMP_PASS);
        idsMap.put("confirmation", RegistrationFormLogic.CHAMP_CONF);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //TODO vérification via RegistrationFromLogic + ajout user dans bdd

        RegistrationFormLogic formLogic = new RegistrationFormLogic();
        UserEntity userEntity = formLogic.registerUser(request);

        if (userEntity != null) {
            if (formLogic.getErrors().isEmpty()) {
                try {
                    FactoryProvider.getDAOFactory().getUserDAO().addUser(userEntity);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/registrationConfirmation.jsp").forward(request, response);
                } catch (SQLException e) {
                    e.printStackTrace(response.getWriter());
                }
            } else {
                request.setAttribute("id", idsMap);
                request.setAttribute("error", formLogic.getErrors());
                request.setAttribute("oldValues", formLogic.getValues());

                this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
            }
        } else {
            response.getWriter().println("Une erreur est survenue.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("id", idsMap);

        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);

    }
}
