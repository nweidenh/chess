package model;
import chess.ChessGame;

public record GameData(Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    public GameData changeWhiteUsername(String newName) {
        return new GameData(gameID, newName, blackUsername, gameName, game);
    }

    public GameData changeBlackUsername(String newName){
        return new GameData(gameID, whiteUsername, newName, gameName, game);
    }

    public GameData changeGameID(Integer newGameID){
        return new GameData(newGameID, whiteUsername, blackUsername, gameName, game);
    }

    public GameData updateBoard(ChessGame newGame) {
        return new GameData(gameID, whiteUsername, blackUsername, gameName, newGame);
    }

    public GameData endGame(ChessGame endedGame){
        return new GameData(gameID, whiteUsername, blackUsername, gameName, endedGame);
    }
}
