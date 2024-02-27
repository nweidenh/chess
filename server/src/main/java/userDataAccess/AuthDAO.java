package userDataAccess;
import model.AuthData;

public interface AuthDAO {

    AuthData createAuth (String username) throws DataAccessException;

    public AuthData getAuth (String username) throws DataAccessException;

    public void deleteAuth (String authToken) throws DataAccessException;

    public void deleteAllAuths() throws DataAccessException;
}
