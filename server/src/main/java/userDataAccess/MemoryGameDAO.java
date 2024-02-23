package userDataAccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{
    private int nextGameID = 1;
    final private static HashMap<Integer, GameData> games = new HashMap<>();

    public void createGame (String gameName){
        new GameData(nextGameID, null,null, gameName, new ChessGame());
        nextGameID += 1;
    }

    public Collection<GameData> getAllGames (){
        return games.values();
    }

    public void deleteAllGames (){
        games.clear();
    }
}
