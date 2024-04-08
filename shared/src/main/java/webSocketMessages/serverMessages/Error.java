package webSocketMessages.serverMessages;

import com.google.gson.Gson;

public class Error extends ServerMessage{

    protected String errorMessage;

    public Error(ServerMessageType type, String errorMessage){
        super(type);
        this.serverMessageType = ServerMessageType.ERROR;
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
