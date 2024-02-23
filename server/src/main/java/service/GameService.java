package service;

import model.*;
import userDataAccess.*;

public class GameService {

    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;
    private final GameDAO gameDataAccess;

    public GameService(UserDAO user, AuthDAO auth, GameDAO game) {
        this.userDataAccess = user;
        this.authDataAccess = auth;
        this.gameDataAccess = game;
    }

    public GameData lstGames(String auth) {
        authDataAccess.getAuth(auth);
        return null;
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
