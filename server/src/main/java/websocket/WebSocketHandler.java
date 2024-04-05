package websocket;

import chess.*;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.AuthService;
import service.GameService;
import service.UserService;
import webSocketMessages.userCommands.*;
import webSocketMessages.serverMessages.*;
import server.Server;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {

    private final UserService userService;
    private final AuthService authService;
    private final GameService gameService;
    private final ConnectionManager connections = new ConnectionManager();

    public WebSocketHandler(UserService userService, AuthService authService, GameService gameService) {
        this.userService = userService;
        this.authService = authService;
        this.gameService = gameService;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException, InvalidMoveException {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case JOIN_PLAYER: {
                JoinPlayer joinCommand = new Gson().fromJson(message, JoinPlayer.class);
                joinPlayer(joinCommand.getGameID(), joinCommand.getAuthString(), session);
                break;
            }
            case JOIN_OBSERVER: {
                JoinObserver joinObvCommand = new Gson().fromJson(message, JoinObserver.class);
                joinObserver(joinObvCommand.getGameID(), joinObvCommand.getAuthString(), session);
                break;
            }
            case MAKE_MOVE: {
                MakeMove moveCommand = new Gson().fromJson(message, MakeMove.class);
                makeMove(moveCommand.getAuthString(), moveCommand.getGameID(), moveCommand.getMove());
                break;
            }
            case LEAVE: {
                Leave leaveCommand = new Gson().fromJson(message, Leave.class);
                leaveGame(leaveCommand.getGameID(), leaveCommand.getAuthString());
                break;
            }
            case RESIGN: {
                Resign resignCommand = new Gson().fromJson(message, Resign.class);
                break;
            }
            case REDRAW: {
                Redraw redrawCommand = new Gson().fromJson(message, Redraw.class);
                redrawBoard(redrawCommand.getGameID(), redrawCommand.getAuthString());
            }
        }
    }

    private void joinPlayer(Integer gameID, String authToken, Session session) throws IOException, DataAccessException {
        String username;
        ChessGame game;
        ChessGame.TeamColor teamColor = ChessGame.TeamColor.WHITE;
        connections.add(gameID, authToken, session);
        username = authService.getAuth(authToken).username();
        if(Objects.equals(gameService.getGame(gameID).blackUsername(), username)){
            teamColor = ChessGame.TeamColor.BLACK;
        }
        game = gameService.getGame(gameID).game();
        var message = String.format("%s joined the game as the %s player", username, teamColor);
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        var loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game);
        connections.broadcast(gameID, authToken, notification);
        connections.sendMessage(gameID, authToken, loadGame);
    }

    private void joinObserver(Integer gameID, String authToken, Session session) throws IOException, DataAccessException {
        String username;
        ChessGame game;
        connections.add(gameID, authToken, session);
        username = authService.getAuth(authToken).username();
        game = gameService.getGame(gameID).game();
        var message = String.format("%s joined the game", username);
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        var loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game);
        connections.broadcast(gameID, authToken, notification);
        connections.sendMessage(gameID, authToken, loadGame);
    }

    private void resign(Integer gameID, String authToken) throws IOException, DataAccessException {
        String username;
        connections.remove(gameID, authToken);
        username = authService.getAuth(authToken).username();
        var message = String.format("%s left the game", username);
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(gameID, authToken, notification);
    }


    private void makeMove(String authToken, int gameID, ChessMove move) throws IOException, DataAccessException, InvalidMoveException {
        String username;
        ChessGame game;
        ChessGame.TeamColor teamColor = null;
        username = authService.getAuth(authToken).username();
        if (Objects.equals(gameService.getGame(gameID).blackUsername(), username)) {
            teamColor = ChessGame.TeamColor.BLACK;
        } else if (Objects.equals(gameService.getGame(gameID).blackUsername(), username)) {
            teamColor = ChessGame.TeamColor.WHITE;
        }
        game = gameService.getGame(gameID).game();
        game.makeMove(move);
        gameService.updateGame(gameID, game);
        var message = String.format("%s player made this move", teamColor);
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        var loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game);
        connections.broadcast(gameID, null, notification);
        connections.broadcastGame(gameID, null, loadGame);
    }


    private void leaveGame(Integer gameID, String authToken) throws IOException, DataAccessException {
        String username;
        connections.remove(gameID, authToken);
        username = authService.getAuth(authToken).username();
        var message = String.format("%s left the game", username);
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(gameID, authToken, notification);
    }

    private void redrawBoard(Integer gameID, String authToken) throws DataAccessException, IOException {
        ChessGame game;
        game = gameService.getGame(gameID).game();
        var loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game);
        connections.sendMessage(gameID, authToken, loadGame);
    }

}