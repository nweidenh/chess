package userDataAccess;
import model.UserData;

public interface UserDAO {

    void createUser (UserData user) throws DataAccessException;

    UserData getUser (UserData user) throws DataAccessException;

    void deleteAllUsers () throws DataAccessException;
}
