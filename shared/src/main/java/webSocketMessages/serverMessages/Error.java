package webSocketMessages.serverMessages;

public class Error extends ServerMessage{

    protected String errorMessage;

    public Error(ServerMessageType type, String errorMessage){
        super(type);
        this.serverMessageType = ServerMessageType.ERROR;
        this.errorMessage = errorMessage;
    }
}
