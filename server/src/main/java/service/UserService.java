package service;

import model.UserData;
import model.AuthData;
import dataAccess.*;

import java.util.HashMap;

public class UserService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

    private final GameDAO gameDataAccess;

    public UserService(UserDAO user, AuthDAO auth, GameDAO game) {
        this.userDataAccess = user;
        this.authDataAccess = auth;
        this.gameDataAccess = game;
    }

    public AuthData register(UserData user) throws DataAccessException{
        userDataAccess.createUser(user);
        return authDataAccess.createAuth(user.username());
    }

    public AuthData login(UserData user) throws DataAccessException{
        UserData user1 = userDataAccess.getUser(user);
        String userName = user1.username();
        return authDataAccess.createAuth(userName);
    }

    public void logout(String auth) throws DataAccessException{
        authDataAccess.getAuth(auth);
        authDataAccess.deleteAuth(auth);
    }

    public HashMap<String, UserData> getUsers() throws DataAccessException{
        return userDataAccess.getUsers();
    }

    //Delete all users
    public void deleteAll() throws DataAccessException{
        userDataAccess.deleteAllUsers();
    }

}
