package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand{

    protected Integer gameID;
    protected ChessGame.TeamColor pColor;
    public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor playerColor){
        super(authToken);
        this.commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        this.pColor = playerColor;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getpColor() {
        return pColor;
    }
}
