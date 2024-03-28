package webSocketMessages.serverMessages;

import webSocketMessages.userCommands.UserGameCommand;

public class LoadGame extends ServerMessage{

    protected Integer gameID;

    public LoadGame(ServerMessageType type, Integer gameID){
        super(type);
        this.serverMessageType = ServerMessageType.LOAD_GAME;
        this.gameID = gameID;
    }
}
