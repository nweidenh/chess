package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;
import java.sql.*;

public class SQLGameDAO  implements GameDAO{

    public SQLGameDAO() throws DataAccessException{
        configureDatabase();
    }
    public GameData createGame (String gameName) throws DataAccessException{
        var statement = "INSERT INTO Games (whiteUsername, blackUsername, gameName, game, json) VALUES (?, ?, ?, ?, ?)";
        ChessGame chessToInsert = new ChessGame();
        chessToInsert.getBoard().resetBoard();
        chessToInsert.setTeamTurn(ChessGame.TeamColor.WHITE);
        GameData createdGame = new GameData(0, null, null, gameName, chessToInsert);
        var json = new Gson().toJson(createdGame);
        var newID = executeUpdate(statement, createdGame.whiteUsername(), createdGame.blackUsername(), createdGame.gameName(), new ChessGame(), json);
        return createdGame.changeGameID(newID);
    }

    public Collection<GameData> getAllGames () throws DataAccessException{
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game, json FROM Games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return result;
    }

    public GameData getGame(int gameID) throws DataAccessException{
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game, json FROM Games WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    } else {
                        throw new DataAccessException(400, "Error: unauthorized");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(400, String.format("Unable to read data: %s", e.getMessage()));
        }
    }

    public void updateGame(GameData game) throws DataAccessException{
        var statement = "UPDATE Games SET whiteUsername=?, blackUsername=?, gameName=?, game=?, json=? WHERE gameID=?";
        var json = new Gson().toJson(game);
        executeUpdate(statement, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game(), json, game.gameID());
    };

    public void deleteAllGames () throws DataAccessException{
        var statement = "TRUNCATE Games";
        executeUpdate(statement);
    };

    private GameData readGame(ResultSet rs) throws SQLException {
        var id = rs.getInt("gameID");
        var json = rs.getString("json");
        var game = new Gson().fromJson(json, GameData.class);
        return game.changeGameID(id);
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
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
                    int myint = rs.getInt(1);
                    return myint;
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
              gameID int not null,
              whiteUsername varchar(256),
              blackUsername varchar(256),
              gameName varchar(256) NOT NULL,
              game longtext,
              json TEXT DEFAULT NULL,
              PRIMARY KEY (gameID)
            )""", """
            CREATE TABLE IF NOT EXISTS Auths  (
              authToken varchar(256) NOT NULL,
              username varchar(256) NOT NULL,
              json TEXT DEFAULT NULL,
              PRIMARY KEY (authToken),
              INDEX(username)
            )
            """, """
            ALTER TABLE Games MODIFY gameID int NOT NULL AUTO_INCREMENT"""
    };

    private void configureDatabase() throws DataAccessException {
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
}
