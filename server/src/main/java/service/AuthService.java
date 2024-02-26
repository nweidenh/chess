package service;

import model.AuthData;
import model.UserData;
import userDataAccess.AuthDAO;
import userDataAccess.GameDAO;
import userDataAccess.UserDAO;

public class AuthService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

    private final GameDAO gameDataAccess;

    public AuthService(UserDAO user, AuthDAO auth, GameDAO game) {
        this.userDataAccess = user;
        this.authDataAccess = auth;
        this.gameDataAccess = game;
    }


    //Delete all auth tokens
    public void deleteAll(){
        authDataAccess.deleteAllAuths();
    }
}
