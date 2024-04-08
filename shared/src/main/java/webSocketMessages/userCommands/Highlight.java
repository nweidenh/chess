package webSocketMessages.userCommands;

import chess.ChessPosition;

public class Highlight extends UserGameCommand{

    protected Integer gameID;
    protected ChessPosition start;

    public Highlight(String authToken, Integer gameID, ChessPosition start){
        super(authToken);
        this.commandType = CommandType.HIGHLIGHT;
        this.gameID = gameID;
        this.start = start;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessPosition getStart() {
        return start;
    }
}
