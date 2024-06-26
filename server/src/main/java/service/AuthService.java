package service;

import model.AuthData;
import dataAccess.DataAccessException;
import dataAccess.*;
import model.UserData;

import java.util.HashMap;

public class AuthService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

    private final GameDAO gameDataAccess;

    public AuthService(UserDAO user, AuthDAO auth, GameDAO game) {
        this.userDataAccess = user;
        this.authDataAccess = auth;
        this.gameDataAccess = game;
    }

    public HashMap<String, AuthData> getAuths() throws DataAccessException{
        return authDataAccess.getAuths();
    }

    public AuthData getAuth(String authToken) throws DataAccessException{
        return authDataAccess.getAuth(authToken);
    }

    //Delete all auth tokens
    public void deleteAll() throws DataAccessException {
        authDataAccess.deleteAllAuths();
    }
}
