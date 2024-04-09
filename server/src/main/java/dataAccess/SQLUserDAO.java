package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SQLUserDAO  implements UserDAO{

    public SQLUserDAO() throws DataAccessException{
        configureDatabase();
    }

    public void createUser (UserData user) throws DataAccessException{
        UserData dummyUser = null;
        if(user.username() == null || user.password() == null || user.email() == null){
            throw new DataAccessException (400, "Error: bad request");
        }
        else {
            try{
                dummyUser = getUser(user);
            }catch (Exception e){
                var statement = "INSERT INTO Users (username, password, email, json) VALUES (?, ?, ?, ?)";
                UserData hashedUser = new UserData(user.username(), storeUserPassword(user.password()), user.email());
                var json = new Gson().toJson(hashedUser);
                executeUpdate(statement, user.username(), user.password(), user.email(), json);
            } if (dummyUser != null){
                throw new DataAccessException(403, "Error: unauthorized");
            }
        }
    }

    public UserData getUser (UserData user) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email, json FROM Users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, user.username());
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        var json = rs.getString("json");
                        UserData gottenUser = new Gson().fromJson(json, UserData.class);
                        if (!verifyUser(gottenUser.password(), user.password())) {
                            throw new DataAccessException(401, "Error: unauthorized");
                        }
                        return gottenUser;
                    } else {
                        throw new DataAccessException(401, "Error: unauthorized");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(401, String.format("Unable to read data: %s", e.getMessage()));
        }
    };

    public void deleteAllUsers () throws DataAccessException{
        var statement = "TRUNCATE Users";
        executeUpdate(statement);
    };

    public HashMap<String, UserData> getUsers () throws DataAccessException{
        var result = new HashMap<String, UserData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email, json FROM Users";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        var key = rs.getString("username");
                        var value = rs.getString("json");
                        result.put(key, new Gson().fromJson(value, UserData.class));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    };

    static int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof ChessGame p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int myInt = rs.getInt(1);
                    return myInt;
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS Users  (
              username varchar(256) NOT NULL,
              password varchar(256) NOT NULL,
              email varchar(256) NOT NULL,
              json TEXT DEFAULT NULL,
              PRIMARY KEY (username),
              INDEX(password),
              INDEX(email)
            )""", """
            CREATE TABLE IF NOT EXISTS Games  (
              gameID int NOT NULL,
              whiteUsername varchar(256),
              blackUsername varchar(256),
              gameName varchar(256) NOT NULL,
              game longtext,
              json TEXT DEFAULT NULL,
              PRIMARY KEY (gameID),
              INDEX(gameName)
            )""", """
            CREATE TABLE IF NOT EXISTS Auths  (
              authToken varchar(256) NOT NULL,
              username varchar(256) NOT NULL,
              json TEXT DEFAULT NULL,
              PRIMARY KEY (authToken),
              INDEX(username)
            )
            """
    };

    private void configureDatabase() throws DataAccessException {
        configData(createStatements);
    }

    static void configData(String[] createStatements) throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    private String storeUserPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        return hashedPassword;
    }

    boolean verifyUser(String hashedPassword, String providedClearTextPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(providedClearTextPassword, hashedPassword);
    }
}
