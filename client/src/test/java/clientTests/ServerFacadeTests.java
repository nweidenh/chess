package clientTests;

import chess.ChessGame;
import model.GameData;
import model.JoinGameRequest;
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
    public static void init() throws ResponseException{
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverUrl = "http://localhost:" + port;
        testServerFacade = new ServerFacade(serverUrl);
        testServerFacade.deleteAll();
    }

    @AfterEach
    void clearDatabase() throws ResponseException {
        testServerFacade.deleteAll();
    }
    @AfterAll
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

    @Test
    @DisplayName("Failed Register")
    public void failedRegister() throws ResponseException {
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        testServerFacade.register(testUser);
        testServerFacade.logout();
        Throwable ex = assertThrows(ResponseException.class, () -> testServerFacade.register(testUser));
        assertEquals("failure: 403", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Login")
    public void login() throws ResponseException {
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        testServerFacade.register(testUser);
        testServerFacade.logout();
        String auth = testServerFacade.login(testUser);
        assertNotNull(auth);
    }

    @Test
    @DisplayName("Failed Login")
    public void failedLogin() throws ResponseException {
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        Throwable ex = assertThrows(ResponseException.class, () -> testServerFacade.login(testUser));
        assertEquals("failure: 401", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Logout")
    public void logout() throws ResponseException {
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        testServerFacade.register(testUser);
        testServerFacade.logout();
        assertNull(testServerFacade.getAuthToken());
    }

    @Test
    @DisplayName("Failed Logout")
    public void failedLogout() throws ResponseException {
        Throwable ex = assertThrows(ResponseException.class, () -> testServerFacade.logout());
        assertEquals("failure: 401", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Create Game")
    public void createGame() throws ResponseException {
        GameData testGame = new GameData(1, null, null, "yessir", new ChessGame());
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        testServerFacade.register(testUser);
        Integer gameID = testServerFacade.createGame(testGame);
        assertNotNull(gameID);
    }

    @Test
    @DisplayName("Failed Create Game")
    public void failedCreateGame() throws ResponseException {
        GameData testGame = new GameData(1, null, null, "yessir", new ChessGame());
        Throwable ex = assertThrows(ResponseException.class, () -> testServerFacade.createGame(testGame));
        assertEquals("failure: 401", ex.getMessage());
    }

    @Test
    @DisplayName("Successful List Games")
    public void listGames() throws ResponseException {
        GameData testGame = new GameData(1, null, null, "yessir", new ChessGame());
        GameData testGame2 = new GameData(2, null, null, "yessir2", new ChessGame());
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        testServerFacade.register(testUser);
        testServerFacade.createGame(testGame);
        testServerFacade.createGame(testGame2);
        var games = testServerFacade.listGames();
        assertEquals(games.length, 2);
    }

    @Test
    @DisplayName("Failed List Games")
    public void failedListGames() throws ResponseException {
        Throwable ex = assertThrows(ResponseException.class, () -> testServerFacade.listGames());
        System.out.println(ex.getMessage());
        assertEquals("failure: 401", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Join Game")
    public void joinGame() throws ResponseException {
        GameData testGame = new GameData(1, null, null, "yessir", new ChessGame());
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        testServerFacade.register(testUser);
        var gameID = testServerFacade.createGame(testGame);
        testServerFacade.joinGame(new JoinGameRequest("BLACK", gameID));
        var gameList = testServerFacade.listGames();
        GameData game = gameList[0];
        assertEquals(testUser.username(), game.blackUsername());
    }

    @Test
    @DisplayName("Failed Join Game")
    public void failedJoinGame() throws ResponseException {
        GameData testGame = new GameData(1, null, null, "yessir", new ChessGame());
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        testServerFacade.register(testUser);
        testServerFacade.createGame(testGame);
        Throwable ex = assertThrows(ResponseException.class, () -> testServerFacade.joinGame(new JoinGameRequest("BLACK", 12)));
        assertEquals("failure: 400", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Delete All")
    public void deleteAll() throws ResponseException {
        GameData testGame = new GameData(1, null, null, "yessir", new ChessGame());
        UserData testUser = new UserData("u", "PassThis", "kate@gmail.com");
        testServerFacade.register(testUser);
        testServerFacade.createGame(testGame);
        testServerFacade.deleteAll();
        testServerFacade.register(testUser);
        var gameList = testServerFacade.listGames();
        assertEquals(0, gameList.length);
    }
}
