package dataAccess;

import model.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO implements AuthDAO{

    public SQLAuthDAO() throws DataAccessException{
        configureDatabase();
    }

    public AuthData createAuth (String username) throws DataAccessException{
        var statement = "INSERT INTO Auths (username, AuthToken, json) VALUES (?, ?, ?)";
        AuthData authToken = new AuthData(UUID.randomUUID().toString(), username);
        var json = new Gson().toJson(authToken);
        SQLUserDAO.executeUpdate(statement, authToken.username(), authToken.authToken(), json);
        return authToken;
    }

    public AuthData getAuth (String authToken) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken, json FROM Auths WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        var json = rs.getString("json");
                        return new Gson().fromJson(json, AuthData.class);
                    } else {
                        throw new DataAccessException(401, "Error: unauthorized");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(401, String.format("Unable to read data: %s", e.getMessage()));
        }
    }

    public void deleteAuth (String authToken) throws DataAccessException{
        var statement = "DELETE FROM Auths WHERE authToken=?";
        getAuth(authToken);
        SQLUserDAO.executeUpdate(statement, authToken);
    }

    public void deleteAllAuths() throws DataAccessException{
        var statement = "TRUNCATE Auths";
        SQLUserDAO.executeUpdate(statement);
    }

    public HashMap<String, AuthData> getAuths () throws DataAccessException{
        var result = new HashMap<String, AuthData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, authToken, json FROM Auths";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        var key = rs.getString("authToken");
                        var value = rs.getString("json");
                        result.put(key, new Gson().fromJson(value, AuthData.class));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

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
}
