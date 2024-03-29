package websocket;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.*;
import webSocketMessages.serverMessages.*;

import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case JOIN_PLAYER:
                JoinPlayer joinCommand = new Gson().fromJson(message, JoinPlayer.class);
                joinPlayer(joinCommand.getGameID(), joinCommand.getAuthString(), session);
            case JOIN_OBSERVER:
                JoinObserver joinObvCommand = new Gson().fromJson(message, JoinObserver.class);
            case MAKE_MOVE:
                MakeMove moveCommand = new Gson().fromJson(message, MakeMove.class);
            case LEAVE:
                Leave leaveCommand = new Gson().fromJson(message, Leave.class);
                leaveGame(leaveCommand.getAuthString());
            case RESIGN:
                Resign resignCommand = new Gson().fromJson(message, Resign.class);
        }
    }

    private void joinPlayer(Integer gameID, String authToken, Session session) throws IOException {
        connections.add(gameID, authToken, session);
        var message = String.format("%s is in the shop", authToken);
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authToken, notification);
    }

    private void leaveGame(String authToken) throws IOException {
        connections.remove(authToken);
        var message = String.format("%s left the shop", authToken);
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authToken, notification);
    }

    public void makeNoise(String petName, String sound) throws DataAccessException {
        try {
            var message = String.format("%s says %s", petName, sound);
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast("", notification);
        } catch (Exception ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }
}