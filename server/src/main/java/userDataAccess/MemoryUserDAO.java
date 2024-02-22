package userDataAccess;
import java.util.HashMap;

import model.UserData;

public class MemoryUserDAO implements UserDAO{
    final private static HashMap<String, UserData> users = new HashMap<>();

    public void createUser (UserData user){
        users.put(user.username(), user);
    }

    public UserData getUser (String username){
        return users.get(username);
    }

    public void deleteAllUsers(){
        users.clear();
    }
}
