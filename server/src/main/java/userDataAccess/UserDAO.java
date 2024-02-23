package userDataAccess;
import model.UserData;

public interface UserDAO {

    void createUser (UserData user);

    UserData getUser (String username);

    void deleteAllUsers ();
}
