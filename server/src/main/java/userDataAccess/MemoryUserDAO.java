package userDataAccess;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import model.UserData;


public class MemoryUserDAO implements UserDAO{
    final private static HashMap<String, UserData> users = new HashMap<>();

    public void createUser (UserData user) throws DataAccessException{
        if(user.username() == null || user.password() == null || user.email() == null){
            throw new DataAccessException (400, "Error: bad request");
        } else if(users.get(user.username()) != null){
            throw new DataAccessException (403, "Error: already taken");
        } else {
            users.put(user.username(), user);
        }
    }

    public UserData getUser (UserData user) throws DataAccessException{
        if (users.get(user.username()) == null){
            throw new DataAccessException(401, "Error: unauthorized");
        }
         else if (!Objects.equals(user.password(), users.get(user.username()).password())){
            throw new DataAccessException(401, "Error: unauthorized");
        }
        return users.get(user.username());
    }

    public HashMap<String, UserData> getUsers () throws DataAccessException{
        return users;
    }

    public void deleteAllUsers() throws DataAccessException{
        users.clear();
    }
}
