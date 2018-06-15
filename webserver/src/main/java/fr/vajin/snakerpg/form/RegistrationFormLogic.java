package fr.vajin.snakerpg.form;

import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.database.entities.UserEntity;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RegistrationFormLogic {

    public static final String FORM_NAME = "registerForm";

    public static final String CHAMP_EMAIL = "email";

    public static final String CHAMP_PASS = "password";

    public static final String CHAMP_CONF = "confirmation";

    public static final String CHAMP_ALIAS = "alias";

    public static final String CHAMP_ACCOUNT_NAME = "accountName";

    public static final int MIN_LENGTH_ALIAS = 6;
    public static final int MAX_LENGTH_ALIAS = 30;

    public static final int MIN_LENGTH_ACCOUNT_NAME = 6;
    public static final int MAX_LENGTH_ACCOUNT_NAME = 30;

    public static final int MIN_PASSWORD_LENGTH = 6;
    public final static String NULL_EMAIL_ERROR_MSG = "Please enter your email adress";
    public final static String INVALID_EMAIL_ERROR_MSG = "Please enter a valid email address.";

    private Map<String, String> errors;
    private Map<String, String> values;

    public UserEntity registerUser(HttpServletRequest request) {

        UserEntity userEntity = new UserEntity();


        String accountName = request.getParameter(CHAMP_ACCOUNT_NAME);

        String email = request.getParameter(CHAMP_EMAIL);

        String password = request.getParameter(CHAMP_PASS);
        String confirmation = request.getParameter(CHAMP_CONF);

        String alias = request.getParameter(CHAMP_ALIAS);

        this.errors = new HashMap<>();
        this.values = new HashMap<>();

        try {
            accountNameValidation(accountName);
        } catch (Exception e) {
            this.errors.put(CHAMP_ACCOUNT_NAME, e.getMessage());
        }

        values.put(CHAMP_ACCOUNT_NAME, accountName);

        try {
            passwordValidation(password, confirmation);
        } catch (Exception e) {
            this.errors.put(CHAMP_PASS, e.getMessage());
        }

        values.put(CHAMP_PASS, password);

        try {
            emailValidation(email);
        } catch (Exception e) {
            this.errors.put(CHAMP_EMAIL, e.getMessage());
        }

        values.put(CHAMP_EMAIL, email);

        try {
            aliasValidation(alias);
        } catch (Exception e) {
            this.errors.put(CHAMP_ALIAS, e.getMessage());
        }

        values.put(CHAMP_ALIAS, alias);

        userEntity.setAlias(alias);
        userEntity.setAccountName(accountName);
        userEntity.setEmail(email);
        userEntity.setPassword(password);

        return userEntity;
    }

    public void emailValidation(String email) throws Exception {
        if (email == null) {
            throw new Exception(NULL_EMAIL_ERROR_MSG);
        }
        boolean valid = EmailValidator.getInstance(true).isValid(email);
        if (!valid) {
            throw new Exception(INVALID_EMAIL_ERROR_MSG);
        }
    }

    public void aliasValidation(String alias) throws Exception {
        if (alias == null) {
            throw new Exception("Alias can not be empty");
        }
        alias = alias.trim();
        if (alias.length() < MIN_LENGTH_ALIAS || alias.length() > MAX_LENGTH_ALIAS) {
            throw new Exception("Alias must have between " + MIN_LENGTH_ALIAS + " and " + MAX_LENGTH_ALIAS + " characters.");
        }
    }

    public void accountNameValidation(String accountName) throws Exception {
        if (accountName == null) {
            throw new Exception("Account name can not be empty");
        }
        accountName = accountName.trim();
        if (accountName.length() < MIN_LENGTH_ACCOUNT_NAME || accountName.length() > MAX_LENGTH_ACCOUNT_NAME) {
            throw new Exception("Account name must have between " + MIN_LENGTH_ACCOUNT_NAME + " and " + MAX_LENGTH_ACCOUNT_NAME + " characters.");
        }

        Optional<UserEntity> user = FactoryProvider.getDAOFactory().getUserDAO().getUserByAccountName(accountName);
        if (user.isPresent()) {
            throw new Exception("Account name already in use");
        }

    }

    public static final String ERROR_NULL_PASSWORD = "Please enter and confirm your password";
    public static final String ERROR_PASSWORDS_NOT_MATCHING = "The passwords does not match";
    public static final String ERROR_PASSWORD_TOO_SHORT = "Your password must be at least " + MIN_PASSWORD_LENGTH + " characters long";

    public void passwordValidation(String password, String confirmation) throws Exception {
        if (password == null || confirmation == null) {
            throw new Exception(ERROR_NULL_PASSWORD);
        }
        if (!password.equals(confirmation)) {
            throw new Exception(ERROR_PASSWORDS_NOT_MATCHING);
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new Exception(ERROR_PASSWORD_TOO_SHORT);
        }
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }

    public Map<String, String> getValues() {
        return this.values;
    }
}
