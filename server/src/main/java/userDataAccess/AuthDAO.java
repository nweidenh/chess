package userDataAccess;
import model.AuthData;

import java.util.HashMap;

public interface AuthDAO {

    AuthData createAuth (String username) throws DataAccessException;

    AuthData getAuth (String username) throws DataAccessException;

    void deleteAuth (String authToken) throws DataAccessException;

    void deleteAllAuths() throws DataAccessException;

    public HashMap<String, AuthData> getAuths () throws DataAccessException;
}
