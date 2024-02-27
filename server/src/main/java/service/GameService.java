package service;

import model.*;
import userDataAccess.*;

import java.util.Collection;
import java.util.Objects;

public class GameService {

    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;
    private final GameDAO gameDataAccess;

    public GameService(UserDAO user, AuthDAO auth, GameDAO game) {
        this.userDataAccess = user;
        this.authDataAccess = auth;
        this.gameDataAccess = game;
    }

    public Collection<GameData> lstGames(String auth) throws DataAccessException{
        authDataAccess.getAuth(auth);
        return gameDataAccess.getAllGames();
    }

    public createGameResponse createGame(String auth, String gameName) throws DataAccessException{
        authDataAccess.getAuth(auth);
        GameData createdGame = gameDataAccess.createGame(gameName);
        new createGameResponse(createdGame.gameID());
        return new createGameResponse(createdGame.gameID());
    }

    public void joinGame(String authToken, joinGameRequest game) throws DataAccessException{
        AuthData userAuth = authDataAccess.getAuth(authToken);
        GameData joinThisGame = gameDataAccess.getGame(game.gameID());
        if(!Objects.equals(game.playerColor(), "WHITE") && !Objects.equals(game.playerColor(), "BLACK") && game.playerColor() != null){
            throw new DataAccessException(400, "Error: bad request");
        }
        else if(Objects.equals(game.playerColor(), "BLACK")){
            if (joinThisGame.blackUsername() == null){
                GameData joinThisGameBlack = joinThisGame.changeBlackUsername(userAuth.username());
                gameDataAccess.updateGame(joinThisGameBlack);
            } else {
                throw new DataAccessException(403, "Error: already taken");
            }
        }
        else if(Objects.equals(game.playerColor(), "WHITE")){
            if(joinThisGame.whiteUsername() == null){
            GameData joinThisGameWhite = joinThisGame.changeWhiteUsername(userAuth.username());
            gameDataAccess.updateGame(joinThisGameWhite);
            } else {
                throw new DataAccessException(403, "Error: already taken");
            }
        }
    }

    public void deleteAll()throws DataAccessException{
        gameDataAccess.deleteAllGames();
    }
}
