package userDataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    private int nextGameID = 1;
    final private static HashMap<Integer, GameData> games = new HashMap<>();

    public GameData createGame (String gameName){
        GameData createdGame = new GameData(nextGameID, null,null, gameName, new ChessGame());
        this.nextGameID += 1;
        games.put(createdGame.gameID(), createdGame);
        return createdGame;
    }

    public Collection<GameData> getAllGames (){
        return games.values();
    }

    public GameData getGame(int gameID){
        return games.get(gameID);
    }

    public void updateGame(GameData game){
        games.put(game.gameID(), game);
    }

    public void deleteAllGames (){
        games.clear();
    }
}
