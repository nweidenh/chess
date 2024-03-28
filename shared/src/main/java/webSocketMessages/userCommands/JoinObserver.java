package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinObserver extends  UserGameCommand{

    protected Integer gameID;

    public JoinObserver(String authToken, Integer gameID){
        super(authToken);
        this.commandType = CommandType.JOIN_OBSERVER;
        this.gameID = gameID;
    }
}
