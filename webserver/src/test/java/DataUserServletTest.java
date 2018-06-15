import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.servlet.data.DataUserServlet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DataUserServletTest {

    DataUserServlet userServlet = new DataUserServlet();

    @Test
    @DisplayName("userId ok")
    void doPostTest() {

        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("userId", "1");
        MockHttpResponse response = new MockHttpResponse();

        Assertions.assertAll(() -> userServlet.doPost(request, response));

        Assertions.assertEquals("application/json", response.getContentType());

    }

    @Test
    @DisplayName("userId non ok")
    void doPostTestFail() {
        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("userId", "10");
        MockHttpResponse response = new MockHttpResponse();

        Assertions.assertAll(() -> userServlet.doPost(request, response));
    }
/*

    @Test
    @DisplayName("test JSONString")
    void testJSONString(){
        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("userId","1");
        MockHttpResponse response = new MockHttpResponse();

        Assertions.assertAll(() ->userServlet.doPost(request,response));

        Gson gson =new Gson();

        String testUserJSON = gson.toJson(FactoryProvider.getDAOFactory().getUserDAO().getUser(1).get());

        Assertions.assertEquals(testUserJSON,response.getContent());


    }
*/

}
