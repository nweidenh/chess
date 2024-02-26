package userDataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    GameData createGame (String gameName);

    Collection<GameData> getAllGames ();

    GameData getGame(int gameID);

    public void updateGame(GameData game);

    void deleteAllGames ();

}
