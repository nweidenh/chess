package userDataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    void createGame (String gameName);

    Collection<GameData> getAllGames ();

    void deleteAllGames ();
}
