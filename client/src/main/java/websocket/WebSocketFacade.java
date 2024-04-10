package websocket;

import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import model.JoinGameRequest;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler){
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
                    } else if(new Gson().fromJson(message, Error.class).getServerMessageType() == ServerMessage.ServerMessageType.ERROR){
                        Error notification = new Gson().fromJson(message, Error.class);
                        notificationHandler.notifyError(notification);
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            notificationHandler.notifyError(new Error (ServerMessage.ServerMessageType.ERROR, ex.getMessage()));
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("WebSocket connection established.");
    }

    public void joinGame(JoinGameRequest gameToJoin, String authToken) throws IOException {
        try {
            var playerToJoin = new JoinPlayer(authToken, gameToJoin.gameID(), gameToJoin.getTeamColor());
            this.session.getBasicRemote().sendText(new Gson().toJson(playerToJoin));
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public void joinObvGame(JoinGameRequest gameToJoin, String authToken) throws IOException {
        try {
            var playerToJoin = new JoinObserver(authToken, gameToJoin.gameID());
            this.session.getBasicRemote().sendText(new Gson().toJson(playerToJoin));
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public void makeMove(int gameID, String authToken, ChessMove moveToMake) throws IOException {
        try {
            var moveMake = new MakeMove(authToken, gameID, moveToMake);
            this.session.getBasicRemote().sendText(new Gson().toJson(moveMake));
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public void redrawBoard(Integer gameID, String authToken) throws IOException {
        var boardToDraw = new Redraw(authToken, gameID);
        this.session.getBasicRemote().sendText(new Gson().toJson(boardToDraw));
    }

    public void highlightMove(Integer gameID, String authToken, ChessPosition start) throws IOException {
        var boardToHighlight = new Highlight(authToken, gameID, start);
        this.session.getBasicRemote().sendText(new Gson().toJson(boardToHighlight));
    }

    public void leaveGame(Integer gameID, String authToken) throws IOException {
        try {
            var leaveGame = new Leave(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(leaveGame));
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public void resignGame(Integer gameID, String authToken) throws IOException {
        try {
            var resign = new Resign(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(resign));
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

}

