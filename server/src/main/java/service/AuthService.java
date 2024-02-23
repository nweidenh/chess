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

    public AuthData register(UserData user) {
        if(userDataAccess.getUser(user.username()) == null){
            userDataAccess.createUser(user);
            return authDataAccess.createAuth(user.username());
        } return null;
    }

    public AuthData login(UserData user) {
        if(userDataAccess.getUser(user.username()) != null){
            return authDataAccess.createAuth(userDataAccess.getUser(user.username()).username());
        } return null;
    }

    public void logout(String auth) {
        authDataAccess.getAuth(auth);
        authDataAccess.deleteAuth(auth);
    }

    public void delete(){
        userDataAccess.deleteAllUsers();
        authDataAccess.deleteAllAuths();
    }
}
