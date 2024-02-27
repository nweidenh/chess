package userDataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    private int nextGameID = 1;
    final private static HashMap<Integer, GameData> games = new HashMap<>();

    public GameData createGame (String gameName) throws DataAccessException{
        GameData createdGame = new GameData(nextGameID, null,null, gameName, new ChessGame());
        nextGameID += 1;
        games.put(createdGame.gameID(), createdGame);
        return createdGame;
    }

    public Collection<GameData> getAllGames () throws DataAccessException{
        return games.values();
    }

    public GameData getGame(int gameID) throws DataAccessException{
        return games.get(gameID);
    }

    public void updateGame(GameData game) throws DataAccessException{
        games.put(game.gameID(), game);
    }

    public void deleteAllGames () throws DataAccessException{
        games.clear();
        nextGameID = 1;
    }
}
