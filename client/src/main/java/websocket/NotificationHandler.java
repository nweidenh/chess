package websocket;

import webSocketMessages.serverMessages.*;

public interface NotificationHandler {
    void notify(Notification notification);

    void notifyLoad(LoadGame loadedGame);
}
