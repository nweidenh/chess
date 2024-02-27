package userDataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    GameData createGame (String gameName) throws DataAccessException;

    Collection<GameData> getAllGames () throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    public void updateGame(GameData game) throws DataAccessException;

    void deleteAllGames () throws DataAccessException;

}
