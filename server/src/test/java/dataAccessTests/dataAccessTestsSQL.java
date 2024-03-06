package dataAccessTests;

import chess.ChessGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.*;
import dataAccess.*;
import model.*;


import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class dataAccessTestsSQL {

    UserDAO usersDataAccess;
    AuthDAO authsDataAccess;
    GameDAO gamesDataAccess;

    public dataAccessTestsSQL(){
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
    @DisplayName("Successful Get User")
    void getTheUser() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        UserData actualUser;
        usersDataAccess.createUser(user);
        actualUser = usersDataAccess.getUser(user);

        assertEquals(user, actualUser);
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
    @DisplayName("Successful Create Auth")
    void createAuth() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");
        usersDataAccess.createUser(user);
        AuthData actualAuth = authsDataAccess.createAuth(user.username());

        assertNotNull(actualAuth);
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
    @DisplayName("Successful Create Game")
    void createGame() throws DataAccessException{
        gamesDataAccess.createGame("Fun");
        assertNotNull(gamesDataAccess.getGame(1));
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
    @DisplayName("Successful Get Game")
    void getGame() throws DataAccessException{
        var game1 = gamesDataAccess.createGame("Fun");
        var actualGame = gamesDataAccess.getGame(1);

        assertEquals(game1, actualGame);
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
