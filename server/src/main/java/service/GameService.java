package service;

import chess.*;
import model.*;
import dataAccess.*;

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

    public CreateGameResponse createGame(String auth, String gameName) throws DataAccessException{
        authDataAccess.getAuth(auth);
        GameData createdGame = gameDataAccess.createGame(gameName);
        return new CreateGameResponse(createdGame.gameID());
    }

    public void joinGame(String authToken, JoinGameRequest game) throws DataAccessException{
        AuthData userAuth = authDataAccess.getAuth(authToken);
        GameData joinThisGame = gameDataAccess.getGame(game.gameID());
        if(game.playerColor() != null && !Objects.equals(game.playerColor(), "WHITE") && !Objects.equals(game.playerColor(), "BLACK")){
            throw new DataAccessException(400, "Error: bad request");
        } else if(Objects.equals(game.playerColor(), "BLACK")){
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

    public void updateGame(int gameID, ChessGame updatedGame) throws DataAccessException {
        GameData gameToBeReplaced = getGame(gameID);
        GameData replacingGameData = gameToBeReplaced.updateBoard(updatedGame);
        gameDataAccess.updateGame(replacingGameData);
    }

    public void finishGame (int gameID) throws DataAccessException {
        GameData gameToEnd = gameDataAccess.getGame(gameID);
        ChessGame endedGame = gameToEnd.game();
        endedGame.setTeamTurn(null);
        gameDataAccess.updateGame(gameToEnd.endGame(endedGame));
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return gameDataAccess.getGame(gameID);
    }

    public void leaveGame(int gameID, ChessGame.TeamColor teamColor) throws DataAccessException {
        GameData leftGame;
        if (teamColor == ChessGame.TeamColor.WHITE){
            leftGame = gameDataAccess.getGame(gameID).changeWhiteUsername(null);
            gameDataAccess.updateGame(leftGame);
        } else if (teamColor == ChessGame.TeamColor.BLACK) {
            leftGame = gameDataAccess.getGame(gameID).changeBlackUsername(null);
            gameDataAccess.updateGame(leftGame);
        }
    }

    public void deleteAll()throws DataAccessException{
        gameDataAccess.deleteAllGames();
    }
}
