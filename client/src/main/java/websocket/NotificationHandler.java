package websocket;

import webSocketMessages.serverMessages.*;
import webSocketMessages.serverMessages.Error;


public interface NotificationHandler {
    void notify(Notification notification);

    void notifyLoad(LoadGame loadedGame);

    void notifyError(Error errorMessage);

}
