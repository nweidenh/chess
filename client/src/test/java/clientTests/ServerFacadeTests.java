package clientTests;

import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import clientOpps.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    private static String serverUrl;
    private static ServerFacade testServerFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverUrl = "http://localhost:" + port;
        testServerFacade = new ServerFacade(serverUrl);
    }

    @AfterAll
    static void clearDatabase() throws ResponseException {
        testServerFacade.deleteAll();
        stopServer();
    }
    static void stopServer() {
        server.stop();
    }


    @Test
    @DisplayName("Successful Register")
    public void register() throws ResponseException {
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        try {
            String auth = testServerFacade.register(testUser);
            assertNotNull(auth);
        } catch(ResponseException ex){
            throw new ResponseException(500, ex.getMessage());
        }
    }

}
