package webSocketMessages.userCommands;

public class Redraw extends UserGameCommand{

    protected Integer gameID;

    public Redraw(String authToken, Integer gameID){
        super(authToken);
        this.commandType = UserGameCommand.CommandType.REDRAW;
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }
}
