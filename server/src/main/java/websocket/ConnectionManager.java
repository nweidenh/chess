package websocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, ArrayList<Connection>> connections = new ConcurrentHashMap<>();

    public void add(Integer gameID, String authToken, Session session) {
        var connection = new Connection(authToken, session);
        if(connections.get(gameID) != null){
            ArrayList<Connection> conList = connections.get(gameID);
            conList.add(connection);
            connections.put(gameID, conList);
        } else {
            ArrayList<Connection> conList = new ArrayList<>();
            conList.add(connection);
            connections.put(gameID, conList);
        }
    }

    public void remove(int gameID, String authToken) {
        var removeList = new ArrayList<Connection>();
        ArrayList<Connection> connectionList = connections.get(gameID);
        for (var c : connectionList){
            if (Objects.equals(c.authToken, authToken)){
                removeList.add(c);
            }
        } for (var c : removeList) {
            connectionList.remove(c);
        } connections.put(gameID, connectionList);
    }

    public void broadcast(int gameID, String excludeAuthToken, Notification notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.get(gameID)) {
                if (c.session.isOpen()) {
                    if (!c.authToken.equals(excludeAuthToken)) {
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

    public void broadcastGame(int gameID, String excludeAuthToken, LoadGame notification) throws IOException {
        for (var c : connections.get(gameID)) {
            if (c.session.isOpen()) {
                if (!c.authToken.equals(excludeAuthToken)) {
                    c.send(notification.toString());
                }
            }
        }
    }

    public void sendMessage(int gameID, String authToken, LoadGame notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.get(gameID)) {
            if (c.session.isOpen()) {
                if (c.authToken.equals(authToken)) {
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
