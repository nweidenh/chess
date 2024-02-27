package service;

import model.AuthData;
import model.UserData;
import userDataAccess.AuthDAO;
import userDataAccess.DataAccessException;
import userDataAccess.GameDAO;
import userDataAccess.UserDAO;

import javax.xml.crypto.Data;
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

    //Delete all auth tokens
    public void deleteAll() throws DataAccessException {
        authDataAccess.deleteAllAuths();
    }
}
