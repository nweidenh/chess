package webSocketMessages.serverMessages;

import com.google.gson.Gson;

public class Notification extends ServerMessage{
    protected String message;

    public Notification(ServerMessageType type, String message){
        super(type);
        this.serverMessageType = ServerMessageType.NOTIFICATION;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
