import fr.vajin.snakerpg.database.entities.UserEntity;
import fr.vajin.snakerpg.form.LoginFormLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

public class LoginFormLogicTest {


    @Test
    @DisplayName("Test Account Name Validation")
    public void testAccountNameValidation() {
        LoginFormLogic loginFormLogic = new LoginFormLogic();
        Assertions.assertThrows(Exception.class, () -> loginFormLogic.accountNameValidation(null), LoginFormLogic.ERROR_NO_ACCOUNT_NAME);
        Assertions.assertThrows(Exception.class, () -> loginFormLogic.accountNameValidation(""), LoginFormLogic.ERROR_NO_ACCOUNT_NAME);
        Assertions.assertAll(() -> loginFormLogic.accountNameValidation("anAccountName"));
    }

    @Test
    @DisplayName("Test Password Validation")
    public void testPasswordValidation() {
        LoginFormLogic loginFormLogic = new LoginFormLogic();
        Assertions.assertThrows(Exception.class, () -> loginFormLogic.passwordValidation(null), LoginFormLogic.ERROR_NO_PASSWORD);
        Assertions.assertThrows(Exception.class, () -> loginFormLogic.passwordValidation(""), LoginFormLogic.ERROR_NO_PASSWORD);
        Assertions.assertAll(() -> loginFormLogic.passwordValidation("apassword"));
    }

    @Test
    @DisplayName("Test loginValidation")
    public void testLoginValidation() {
        LoginFormLogic loginFormLogic = new LoginFormLogic();


        String accountName = "user1";
        String password = "123456";

        MockHttpRequest mockRequest = new MockHttpRequest();
        mockRequest.addParameters(LoginFormLogic.CHAMP_ACCOUNT_NAME, accountName);
        mockRequest.addParameters(LoginFormLogic.CHAMP_PASSWORD, password);

        Optional<UserEntity> optional = loginFormLogic.logInUser(mockRequest);
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertTrue(loginFormLogic.getErrors().isEmpty());
        Assertions.assertEquals(accountName, loginFormLogic.getValues().get(LoginFormLogic.CHAMP_ACCOUNT_NAME));
        Assertions.assertEquals(password, loginFormLogic.getValues().get(LoginFormLogic.CHAMP_PASSWORD));
    }

    @Test
    @DisplayName("Test failed loginValidation")
    public void testFailLoginValidation() {
        LoginFormLogic loginFormLogic = new LoginFormLogic();

        String accountName = "NotAUser";
        String password = "123456";

        MockHttpRequest mockRequest = new MockHttpRequest();
        mockRequest.addParameters(LoginFormLogic.CHAMP_ACCOUNT_NAME, accountName);
        mockRequest.addParameters(LoginFormLogic.CHAMP_PASSWORD, password);

        Optional<UserEntity> optional = loginFormLogic.logInUser(mockRequest);
        Assertions.assertFalse(optional.isPresent());
        Assertions.assertTrue(loginFormLogic.getErrors().isEmpty());

        Assertions.assertEquals(accountName, loginFormLogic.getValues().get(LoginFormLogic.CHAMP_ACCOUNT_NAME));
        Assertions.assertEquals(password, loginFormLogic.getValues().get(LoginFormLogic.CHAMP_PASSWORD));

        accountName = "user1";
        password = null;
        mockRequest = new MockHttpRequest();
        mockRequest.addParameters(LoginFormLogic.CHAMP_ACCOUNT_NAME, accountName);
        mockRequest.addParameters(LoginFormLogic.CHAMP_PASSWORD, password);

        optional = loginFormLogic.logInUser(mockRequest);
        Assertions.assertFalse(optional.isPresent());
        Map<String, String> error = loginFormLogic.getErrors();
        Assertions.assertNotNull(error.get(LoginFormLogic.CHAMP_PASSWORD));

        accountName = null;
        password = "123166";
        mockRequest = new MockHttpRequest();
        mockRequest.addParameters(LoginFormLogic.CHAMP_ACCOUNT_NAME, accountName);
        mockRequest.addParameters(LoginFormLogic.CHAMP_PASSWORD, password);

        optional = loginFormLogic.logInUser(mockRequest);
        Assertions.assertFalse(optional.isPresent());
        error = loginFormLogic.getErrors();
        Assertions.assertNotNull(error.get(LoginFormLogic.CHAMP_ACCOUNT_NAME));
    }
}
