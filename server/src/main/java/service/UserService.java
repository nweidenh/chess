package service;

import model.UserData;
import model.AuthData;
import userDataAccess.*;

public class UserService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

    private final GameDAO gameDataAccess;

    public UserService(UserDAO user, AuthDAO auth, GameDAO game) {
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

    //Delete all users
    public void deleteAll(){
        userDataAccess.deleteAllUsers();
    }
}
