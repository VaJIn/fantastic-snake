import fr.vajin.snakerpg.form.RegistrationFormLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class RegistrationFormLogicTest {

    private RegistrationFormLogic registrationFormLogic;

    public RegistrationFormLogicTest() {
        this.registrationFormLogic = new RegistrationFormLogic();
    }

    @Test
    @DisplayName("Valid email")
    public void validEmailTest() {
        /*
        Valid email address : see https://en.wikipedia.org/wiki/Email_address#Syntax
         */
        String[] validTestCase = {
                "email@domain.com",
                "firstname.lastname@domain.com",
                "email@subdomain.domain.com",
                "firstname+lastname@domain.com",
                "email@[123.123.123.123]",
                "\"email\"@domain.com",
                "1234567890@domain.com",
                "email@domain-one.com",
                "_______@domain.com",
                "email@domain.name",
                "email@domain.co.jp",
                "firstname-lastname@domain.com"
        };

        for (String testCase : validTestCase) {
            Assertions.assertAll(testCase, () -> registrationFormLogic.emailValidation(testCase));
        }

    }

    @Test
    @DisplayName("Invalid email")
    public void failingEmailTest() {


        //Null string
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.emailValidation(null), RegistrationFormLogic.NULL_EMAIL_ERROR_MSG);

        //Empty string
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.emailValidation(""), RegistrationFormLogic.NULL_EMAIL_ERROR_MSG);

        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.emailValidation("     "), RegistrationFormLogic.NULL_EMAIL_ERROR_MSG);

        //Plain text
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.emailValidation("plaintext"), RegistrationFormLogic.INVALID_EMAIL_ERROR_MSG);

        //Invalid domain
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.emailValidation("email@123.123.123.123"), RegistrationFormLogic.INVALID_EMAIL_ERROR_MSG);

        //Don't need to test tons of email format since we use the Apache Commons Email validator, which is already tested
    }

    @Test
    @DisplayName("Password validation test")
    public void testPasswordValidation() {
        Assertions.assertAll(() -> registrationFormLogic.passwordValidation("123456", "123456"));
    }

    @Test
    @DisplayName("Password Validation : null input")
    public void testPasswordValidationNullInput() {
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.passwordValidation(null, "yoloswag"));
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.passwordValidation("yoloswag", null));
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.passwordValidation(null, null));
    }

    @Test
    @DisplayName("Password Validation : Length")
    public void testPasswordValidationWithLength() {
        //Too short
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.passwordValidation("123", "123"));
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.passwordValidation("", ""));
    }


    @Test
    @DisplayName("Password Validation : password != confirm")
    public void testPasswordValidationNonMatchingInputs() {
        Assertions.assertThrows(Exception.class, () -> registrationFormLogic.passwordValidation("ceciEstUnMotDePasse", "ceciEstUnAutreMotDePasse"));
    }
}
