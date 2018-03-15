import com.google.gson.Gson;
import fr.vajin.snakerpg.FactoryProvider;
import fr.vajin.snakerpg.servlet.data.DataGameServlet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;

public class DataGameServletTest {
    DataGameServlet gameServlet = new DataGameServlet();

    @Test
    @DisplayName("gameId ok")
    void doPostTest() {

        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("gameId", "1");
        MockHttpResponse response = new MockHttpResponse();

        Assertions.assertAll(() -> gameServlet.doPost(request, response));

        Assertions.assertEquals("application/json", response.getContentType());


    }

    @Test
    @DisplayName("gameId non ok")
    void doPostTestFail() {
        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("gameId", "100");
        MockHttpResponse response = new MockHttpResponse();

        Assertions.assertAll(() -> gameServlet.doPost(request, response));
        Assertions.assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
    }
/*
    @Test
    @DisplayName("test JSONString")
    void testJSONString(){
        MockHttpRequest request = new MockHttpRequest();
        request.addParameters("gameId","1");
        MockHttpResponse response = new MockHttpResponse();

        Assertions.assertAll(() ->gameServlet.doPost(request,response));

        Gson gson =new Gson();
        String testGameJSON = gson.toJson(FactoryProvider.getDAOFactory().getGameDAO().getGame(1).get());

        Assertions.assertEquals(testGameJSON, response.getContent());


    }*/
}
