package dataAccessTests;

import chess.ChessGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.*;
import dataAccess.*;
import model.*;


import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class dataAccessTestsSQL {

    private final UserService userService;
    private final AuthService authService;
    private final GameService gameService;

    public dataAccessTestsSQL(){
        UserDAO usersDataAccess = null;
        try {
            usersDataAccess = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        AuthDAO authsDataAccess = null;
        try {
            authsDataAccess = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        GameDAO gamesDataAccess = null;
        try {
            gamesDataAccess = new SQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        userService = new UserService(usersDataAccess, authsDataAccess, gamesDataAccess);
        authService = new AuthService(usersDataAccess, authsDataAccess, gamesDataAccess);
        gameService = new GameService(usersDataAccess, authsDataAccess, gamesDataAccess);
    }

    @BeforeEach
    public

    @Test
    @DisplayName("Successful Create")
    void create() throws DataAccessException{
        var user = new UserData("newUser", "strongPassword", "advancedEmail@gmail.com");

    }
}
