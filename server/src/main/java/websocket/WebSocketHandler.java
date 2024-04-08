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
import webSocketMessages.serverMessages.Error;
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
                resign(resignCommand.getGameID(), resignCommand.getAuthString());
                break;
            }
            case REDRAW: {
                Redraw redrawCommand = new Gson().fromJson(message, Redraw.class);
                redrawBoard(redrawCommand.getGameID(), redrawCommand.getAuthString());
                break;
            }
            case HIGHLIGHT: {
                Highlight highlightCommand = new Gson().fromJson(message, Highlight.class);
                highlightMoves(highlightCommand.getGameID(), highlightCommand.getAuthString(), highlightCommand.getStart());
            }
        }
    }

    private void joinPlayer(Integer gameID, String authToken, Session session) throws IOException, DataAccessException {
        try {
            String username;
            ChessGame game;
            ChessGame.TeamColor teamColor = null;
            connections.add(gameID, authToken, session);
            username = authService.getAuth(authToken).username();
            if (Objects.equals(gameService.getGame(gameID).blackUsername(), username)) {
                teamColor = ChessGame.TeamColor.BLACK;
            } else if (Objects.equals(gameService.getGame(gameID).whiteUsername(), username)) {
                teamColor = ChessGame.TeamColor.WHITE; }
            else{
                throw new DataAccessException(500, "Not on either team");
            }
            game = gameService.getGame(gameID).game();
            var message = String.format("%s joined the game as the %s player", username, teamColor);
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            var loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game);
            connections.broadcast(gameID, authToken, notification);
            connections.sendMessage(gameID, authToken, loadGame);
        }catch (IOException | DataAccessException ex){
            String errorMessage = ex.getMessage();
            connections.sendMessage(gameID, authToken, new Error(ServerMessage.ServerMessageType.ERROR, errorMessage));
        }

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
        try {
            String username;
            username = authService.getAuth(authToken).username();
            if (!Objects.equals(gameService.getGame(gameID).blackUsername(), username) && !Objects.equals(gameService.getGame(gameID).blackUsername(), username)) {
                throw new DataAccessException(500, "Cannot resign as observer");
            }
            var message = String.format("%s resigned from the game", username);
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            var userNotification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, "You resigned from the game");
            connections.broadcast(gameID, authToken, notification);
            connections.sendMessage(gameID, authToken, userNotification);
        } catch (IOException | DataAccessException ex){
            connections.sendMessage(gameID, authToken, new Error(ServerMessage.ServerMessageType.ERROR, ex.getMessage()));
        }
    }

    private void makeMove(String authToken, int gameID, ChessMove move) throws IOException, DataAccessException, InvalidMoveException {
        try {
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
            char column = convertToLetter(move.getStartPosition().getColumn());
            char column2 = convertToLetter(move.getEndPosition().getColumn());
            gameService.updateGame(gameID, game);
            var message = String.format("%s made a move. They went from space %d%c to space %d%c", username, move.getStartPosition().getRow(), column, move.getEndPosition().getRow(), column2);
            message += game.getBoard().toString();
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            var loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game);
            connections.broadcast(gameID, authToken, notification);
            connections.broadcastGame(gameID, null, loadGame);
        } catch (InvalidMoveException | IOException | DataAccessException ex){
            connections.sendMessage(gameID, authToken, new Error(ServerMessage.ServerMessageType.ERROR, ex.getMessage()));
        }
    }

    private char convertToLetter(int col){
        return (char) ('A' + col - 1);
    }

    private void leaveGame(Integer gameID, String authToken) throws IOException, DataAccessException {
        String username;
        ChessGame.TeamColor teamColor = null;
        connections.remove(gameID, authToken);
        username = authService.getAuth(authToken).username();
        if (Objects.equals(gameService.getGame(gameID).blackUsername(), username)) {
            teamColor = ChessGame.TeamColor.BLACK;
        } else if (Objects.equals(gameService.getGame(gameID).blackUsername(), username)) {
            teamColor = ChessGame.TeamColor.WHITE;
        } gameService.leaveGame(gameID, teamColor);
        var message = String.format("%s left the game", username);
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        var userNotification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, "You left the game");
        connections.broadcast(gameID, authToken, notification);
        connections.sendMessage(gameID, authToken, userNotification);
    }

    private void redrawBoard(Integer gameID, String authToken) throws DataAccessException, IOException {
        ChessGame game;
        game = gameService.getGame(gameID).game();
        var loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, game);
        connections.sendMessage(gameID, authToken, loadGame);
    }

    private void highlightMoves(Integer gameID, String authToken, ChessPosition start) throws DataAccessException, IOException {
        ChessGame game;
        game = gameService.getGame(gameID).game();
        String message = game.findHighlight(start);
        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.sendMessage(gameID, authToken, notification);
    }

}