import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.servlet.data.DataSnakeServlet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;

public class DataSnakeServletTest {

    DataSnakeServlet snakeServlet = new DataSnakeServlet();

    @Test
    @DisplayName("snakeId ok")
    void doPostTest() {

        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("snakeId", "1");
        MockHttpResponse response = new MockHttpResponse();

        Assertions.assertAll(() -> snakeServlet.doPost(request, response));

        Assertions.assertEquals("application/json", response.getContentType());


    }

    @Test
    @DisplayName("snakeId non ok")
    void doPostTestFail() {
        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("snakeId", "100");
        MockHttpResponse response = new MockHttpResponse();

        Assertions.assertAll(() -> snakeServlet.doPost(request, response));
        Assertions.assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());

    }

    @Test
    @DisplayName("test JSONString")
    void testJSONString() {
        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("snakeId", "1");
        MockHttpResponse response = new MockHttpResponse();

        Assertions.assertAll(() -> snakeServlet.doPost(request, response));

        Gson gson = new Gson();
        String testUserJSON = gson.toJson(FactoryProvider.getDAOFactory().getSnakeDAO().getSnakeById(1).get());

        Assertions.assertEquals(testUserJSON, response.getContent());


    }

}
