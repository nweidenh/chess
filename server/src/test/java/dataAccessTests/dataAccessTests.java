package dataAccessTests;

import chess.ChessGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dataAccess.*;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class dataAccessTests {

    UserDAO usersDataAccess;
    AuthDAO authsDataAccess;
    GameDAO gamesDataAccess;

    public dataAccessTests(){
        usersDataAccess = null;
        try {
            usersDataAccess = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        authsDataAccess = null;
        try {
            authsDataAccess = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        gamesDataAccess = null;
        try {
            gamesDataAccess = new SQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void delete() throws DataAccessException{
        usersDataAccess.deleteAllUsers();
        gamesDataAccess.deleteAllGames();
        authsDataAccess.deleteAllAuths();
    }

    @Test
    @DisplayName("Successful Create User")
    void createUser() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        assertDoesNotThrow(() -> usersDataAccess.createUser(user));
    }

    @Test
    @DisplayName("Failed Create User")
    void failedCreateUser() throws DataAccessException{
        var user = new UserData("newUser", null, "advancedEmail@gmail.com");
        Throwable ex = assertThrows(DataAccessException.class, () -> usersDataAccess.createUser(user));

        assertEquals("Error: bad request", ex.getMessage());
    }

    @Test
    @DisplayName("Successful Get User")
    void getTheUser() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        UserData actualUser;
        usersDataAccess.createUser(user);
        actualUser = usersDataAccess.getUser(user);

        assertEquals(user, actualUser);
    }

    @Test
    @DisplayName("Failed Get User")
    void failedGetTheUser() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var badUser = new UserData("badUser", "strongPassword", "advancedEmail@gmail.com");
        usersDataAccess.createUser(user);

        DataAccessException ex = assertThrows(DataAccessException.class, () -> usersDataAccess.getUser(badUser));

        assertEquals(401, ex.statusCode());
    }

    @Test
    @DisplayName("Successful Delete All Users")
    void deleteAllUsers() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        usersDataAccess.createUser(user);
        usersDataAccess.deleteAllUsers();

        var actual = usersDataAccess.getUsers();
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Successful Get All Users")
    void getAllUsers() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var user2 = new UserData("newUser2", "strongPassword2", "advancedEmail2@gmail.com");
        HashMap<String, UserData> expected = new HashMap<>();
        usersDataAccess.createUser(user);
        usersDataAccess.createUser(user2);
        expected.put(user.username(), user);
        expected.put(user2.username(), user2);

        var actual = usersDataAccess.getUsers();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Failed Get All Users")
    void failedGetAllUsers() throws DataAccessException{
        var actual = usersDataAccess.getUsers();
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Successful Create Auth")
    void createAuth() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        usersDataAccess.createUser(user);
        AuthData actualAuth = authsDataAccess.createAuth(user.username());

        assertNotNull(actualAuth);
    }

    @Test
    @DisplayName("Failed Create Auth")
    void failedCreateAuth() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var user2 = new UserData("newUser2", "strongPassword", "advancedEmail@gmail.com");
        AuthData actualAuth = authsDataAccess.createAuth(user.username());
        AuthData actualAuth2 = authsDataAccess.createAuth(user2.username());

        assertNotEquals(actualAuth2, actualAuth);
    }

    @Test
    @DisplayName("Successful Get Auth")
    void getAuth() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        usersDataAccess.createUser(user);
        AuthData expectedAuth = authsDataAccess.createAuth(user.username());
        AuthData actualAuth = authsDataAccess.getAuth(expectedAuth.authToken());

        assertEquals(expectedAuth, actualAuth);
    }

    @Test
    @DisplayName("Failed Get Auth")
    void failedGetAuth() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        usersDataAccess.createUser(user);

        DataAccessException ex = assertThrows(DataAccessException.class, () -> authsDataAccess.getAuth("hello"));

        assertEquals(401, ex.statusCode());
    }

    @Test
    @DisplayName("Successful Delete Auth")
    void deleteAuth() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var user2 = new UserData("newUser2", "strongPassword2", "advancedEmail2@gmail.com");
        usersDataAccess.createUser(user);
        usersDataAccess.createUser(user2);
        AuthData deletedAuth = authsDataAccess.createAuth(user.username());
        authsDataAccess.createAuth(user2.username());
        authsDataAccess.deleteAuth(deletedAuth.authToken());
        var actual = authsDataAccess.getAuths();

        assertFalse(actual.containsValue(deletedAuth));
    }

    @Test
    @DisplayName("Failed Delete Auth")
    void failedDeleteAuth() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        authsDataAccess.createAuth(user.username());
        DataAccessException ex = assertThrows(DataAccessException.class, () -> authsDataAccess.deleteAuth("hello"));

        assertEquals(401, ex.statusCode());
    }

    @Test
    @DisplayName("Successful Delete All Auths")
    void deleteAllAuths() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var user2 = new UserData("newUser2", "strongPassword2", "advancedEmail2@gmail.com");
        usersDataAccess.createUser(user);
        usersDataAccess.createUser(user2);
        authsDataAccess.createAuth(user.username());
        authsDataAccess.createAuth(user2.username());
        authsDataAccess.deleteAllAuths();
        var actual = authsDataAccess.getAuths();

        assertEquals(0,actual.size());
    }

    @Test
    @DisplayName("Successful Get All Auths")
    void getAllAuths() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        var user2 = new UserData("newUser2", "strongPassword2", "advancedEmail2@gmail.com");
        HashMap<String, AuthData> expected = new HashMap<>();
        usersDataAccess.createUser(user);
        usersDataAccess.createUser(user2);
        AuthData auth1 = authsDataAccess.createAuth(user.username());
        AuthData auth2 = authsDataAccess.createAuth(user2.username());
        expected.put(auth1.authToken(), auth1);
        expected.put(auth2.authToken(), auth2);
        var actual = authsDataAccess.getAuths();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Failed Get All Auths")
    void failedGetAllAuths() throws DataAccessException{
        var actual = authsDataAccess.getAuths();
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Successful Create Game")
    void createGame() throws DataAccessException{
        gamesDataAccess.createGame("Fun");
        assertNotNull(gamesDataAccess.getGame(1));
    }

    @Test
    @DisplayName("Failed Create Game")
    void failedCreateGame() throws DataAccessException{
        DataAccessException ex = assertThrows(DataAccessException.class, () -> gamesDataAccess.createGame(null));
        assertEquals(500, ex.statusCode());
    }

    @Test
    @DisplayName("Successful Get All Games")
    void getAllGames() throws DataAccessException{
        var game1 = gamesDataAccess.createGame("Fun");
        var game2 = gamesDataAccess.createGame("Fun2");
        var game3 = gamesDataAccess.createGame("Fun3");
        var actualGames = gamesDataAccess.getAllGames();

        ArrayList<GameData> expectedGames = new ArrayList<>();
        expectedGames.add(game1);
        expectedGames.add(game2);
        expectedGames.add(game3);

        assertEquals(actualGames, expectedGames);
    }

    @Test
    @DisplayName("Failed Get All Games")
    void failedGetAllGames() throws DataAccessException{
        var actual = gamesDataAccess.getAllGames();
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("Successful Get Game")
    void getGame() throws DataAccessException{
        var game1 = gamesDataAccess.createGame("Fun");
        var actualGame = gamesDataAccess.getGame(1);

        assertEquals(game1, actualGame);
    }

    @Test
    @DisplayName("Failed Get Game")
    void failedGetGame() throws DataAccessException{
        DataAccessException ex = assertThrows(DataAccessException.class, () -> gamesDataAccess.getGame(1));
        assertEquals(400, ex.statusCode());
    }

    @Test
    @DisplayName("Successful Update Games")
    void updateGame() throws DataAccessException{
        GameData game = new GameData(1, "whiteUser", null, "Fun", new ChessGame());
        gamesDataAccess.createGame("Fun");
        gamesDataAccess.updateGame(game);
        var actualGame = gamesDataAccess.getGame(1);

        assertEquals(game, actualGame);
    }

    @Test
    @DisplayName("Failed Update Games")
    void failedUpdateGame() throws DataAccessException{
        GameData game = new GameData(1, "whiteUser", null, null, new ChessGame());
        gamesDataAccess.createGame("Fun");
        DataAccessException ex = assertThrows(DataAccessException.class, () -> gamesDataAccess.updateGame(game));
        assertEquals(500, ex.statusCode());
    }

    @Test
    @DisplayName("Successful Delete All Games")
    void deleteAllGames() throws DataAccessException{
        gamesDataAccess.createGame("Fun");
        gamesDataAccess.createGame("Fun2");
        gamesDataAccess.createGame("Fun3");
        gamesDataAccess.deleteAllGames();
        var actual = gamesDataAccess.getAllGames();

        assertEquals(0,actual.size());
    }
}
