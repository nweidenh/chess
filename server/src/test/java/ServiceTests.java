import chess.ChessGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.*;
import userDataAccess.*;
import model.*;


import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    private final UserService userService;
    private final AuthService authService;
    private final GameService gameService;

    public ServiceTests(){
        UserDAO usersDataAccess = new MemoryUserDAO();
        AuthDAO authsDataAccess = new MemoryAuthDAO();
        GameDAO gamesDataAccess = new MemoryGameDAO();
        userService = new UserService(usersDataAccess, authsDataAccess, gamesDataAccess);
        authService = new AuthService(usersDataAccess, authsDataAccess, gamesDataAccess);
        gameService = new GameService(usersDataAccess, authsDataAccess, gamesDataAccess);
    }

    @BeforeEach
    void clear() throws DataAccessException{
        userService.deleteAll();
        authService.deleteAll();
        gameService.deleteAll();
    }

    @Test
    @DisplayName("Successful Register")
    void register() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        userService.register(user);

        var users = userService.getUsers();
        assertEquals(1, users.size());
        assertTrue(users.containsKey(user.username()));
        assertTrue(users.containsValue(user));
    }

    @Test
    @DisplayName("Failed Register - 400, bad request")
    void failRegister() throws DataAccessException{
        var user = new UserData("newUser", null, "advancedEmail@gmail.com");
        Throwable ex = assertThrows(DataAccessException.class, () -> userService.register(user));

        assertEquals("Error: bad request", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Login")
    void login() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        userService.logout(auth.authToken());
        var authAfterLogin = userService.login(user);

        assertNotNull(authAfterLogin);
        assertNotNull(authAfterLogin.authToken());
        assertEquals(user.username(), authAfterLogin.username());
    }
    @Test
    @DisplayName("Failed Login - 401 unauthorized")
    void failLogin() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var badUser = new UserData("badUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        userService.logout(auth.authToken());
        Throwable ex = assertThrows(DataAccessException.class, () -> userService.login(badUser));

        assertEquals("Error: unauthorized", ex.getMessage());
    }
    @Test
    @DisplayName("Successful Logout")
    void logout() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        userService.logout(auth.authToken());

        assertFalse(authService.getAuths().containsKey(auth.authToken()));
    }

    @Test
    @DisplayName("Failed Logout - 401 unauthorized")
    void failedLogout() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        userService.register(user);
        var sillyString = "silly";
        Throwable ex = assertThrows(DataAccessException.class, () -> userService.logout(sillyString));

        assertEquals("Error: unauthorized", ex.getMessage());
    }
    @Test
    @DisplayName("Successful List Games")
    void lstGames() throws DataAccessException{
        Collection<GameData> expected = new ArrayList<>();
        expected.add(new GameData(1, null, null, "Fun", new ChessGame()));
        expected.add(new GameData(2, null, null, "Funner", new ChessGame()));
        expected.add(new GameData(3, null, null, "Funnest", new ChessGame()));

        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        gameService.createGame(auth.authToken(), "Fun");
        gameService.createGame(auth.authToken(), "Funner");
        gameService.createGame(auth.authToken(), "Funnest");
        var actual = gameService.lstGames(auth.authToken());

        assertIterableEquals(expected, actual);
    }

    @Test
    @DisplayName("Failed List Games - 401 unauthorized")
    void failedLstGames() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        var sillyString = "thisSilly";
        gameService.createGame(auth.authToken(), "Fun");
        gameService.createGame(auth.authToken(), "Funner");
        gameService.createGame(auth.authToken(), "Funnest");
        Throwable ex = assertThrows(DataAccessException.class, () -> gameService.lstGames(sillyString));

        assertEquals("Error: unauthorized", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Create Game")
    void createGame() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        gameService.createGame(auth.authToken(), "Fun");

        assertNotNull(gameService.getGame(1));
    }

    @Test
    @DisplayName("Failed Create Game - 400 bad request")
    void failedCreateGame() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        Throwable ex = assertThrows(DataAccessException.class, () -> gameService.createGame(auth.authToken(), null));

        assertEquals("Error: bad request", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Join Game")
    void joinGame() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        gameService.createGame(auth.authToken(), "Fun");
        var joinGame = new JoinGameRequest("BLACK", 1);
        gameService.joinGame(auth.authToken(), joinGame);

        assertNotNull(gameService.getGame(1).blackUsername());
    }

    @Test
    @DisplayName("Failed Join Game - bad request")
    void failedJoinGame() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        gameService.createGame(auth.authToken(), "Fun");
        var joinGame = new JoinGameRequest("Black", 1);
        Throwable ex = assertThrows(DataAccessException.class, () -> gameService.joinGame(auth.authToken(), joinGame));

        assertEquals("Error: bad request", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Clear")
    void clearAll() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var auth = userService.register(user);
        gameService.createGame(auth.authToken(), "Fun");

        gameService.deleteAll();
        assertEquals(0, gameService.lstGames(auth.authToken()).size());

        userService.deleteAll();
        authService.deleteAll();
        assertEquals(0, userService.getUsers().size());
        assertEquals(0, authService.getAuths().size());
    }

}
