package userDataAccess;
import model.AuthData;

public interface AuthDAO {

    AuthData createAuth (String username) throws DataAccessException;

    AuthData getAuth (String username) throws DataAccessException;

    void deleteAuth (String authToken) throws DataAccessException;

    void deleteAllAuths() throws DataAccessException;
}
