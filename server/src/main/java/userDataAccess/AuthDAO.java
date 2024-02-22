package userDataAccess;
import model.AuthData;

public interface AuthDAO {

    AuthData createAuth (String username);

    public AuthData getAuth (String username);

    public void deleteAllAuths();
}
