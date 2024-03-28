package websocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Connection> connections = new ConcurrentHashMap<>();

    public void add(Integer gameID, String authToken, Session session) {
        var connection = new Connection(authToken, session);
        connections.put(gameID, connection);
    }

    public void remove(String visitorName) {
        connections.remove(visitorName);
    }

    public void broadcast(String excludeVisitorName, Notification notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.authToken.equals(excludeVisitorName)) {
                    c.send(notification.toString());
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.authToken);
        }
    }
}
