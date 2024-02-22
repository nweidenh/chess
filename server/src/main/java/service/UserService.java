package service;

import model.UserData;
import model.AuthData;
import userDataAccess.UserDAO;
import userDataAccess.AuthDAO;

public class UserService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

    public UserService(UserDAO user, AuthDAO auth) {
        this.userDataAccess = user;
        this.authDataAccess = auth;
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

    public void logout(UserData user) {
    }

    public void delete(){
        userDataAccess.deleteAllUsers();
        authDataAccess.deleteAllAuths();
    }
}
