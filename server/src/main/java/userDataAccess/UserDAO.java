package userDataAccess;
import model.UserData;

import java.util.HashMap;

public interface UserDAO {

    void createUser (UserData user) throws DataAccessException;

    UserData getUser (UserData user) throws DataAccessException;

    void deleteAllUsers () throws DataAccessException;

    HashMap<String, UserData> getUsers () throws DataAccessException;
}
