package websocket;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.JoinGameRequest;
import webSocketMessages.serverMessages.*;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws DataAccessException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    if (new Gson().fromJson(message, Notification.class).getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
                        Notification notification = new Gson().fromJson(message, Notification.class);
                        notificationHandler.notify(notification);
                    } else if(new Gson().fromJson(message, LoadGame.class).getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
                        LoadGame notification = new Gson().fromJson(message, LoadGame.class);
                        notificationHandler.notifyLoad(notification);
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("WebSocket connection established.");
    }

    public void joinGame(JoinGameRequest gameToJoin, String authToken) throws DataAccessException {
        try {
            var playerToJoin = new JoinPlayer(authToken, gameToJoin.gameID(), gameToJoin.getTeamColor());
            this.session.getBasicRemote().sendText(new Gson().toJson(playerToJoin));
        } catch (IOException ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    public void joinObvGame(JoinGameRequest gameToJoin, String authToken) throws DataAccessException {
        try {
            var playerToJoin = new JoinObserver(authToken, gameToJoin.gameID());
            this.session.getBasicRemote().sendText(new Gson().toJson(playerToJoin));
        } catch (IOException ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

    public void redrawBoard(Integer gameID, String authToken) throws DataAccessException, IOException {
        var boardToDraw = new Redraw(authToken, gameID);
        this.session.getBasicRemote().sendText(new Gson().toJson(boardToDraw));
    }

    public void leaveGame(Integer gameID, String authToken) throws DataAccessException {
        try {
            var leaveGame = new Leave(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(leaveGame));
            this.session.close();
        } catch (IOException ex) {
            throw new DataAccessException(500, ex.getMessage());
        }
    }

}

