package clientOpps;

import dataAccess.DataAccessException;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;

public class GameUi {
    private final String serverUrl;
    private WebSocketFacade ws;
    private final NotificationHandler notificationHandler;
    public String authToken = null;

    public GameUi(String serverUrl, NotificationHandler notificationHandler) throws DataAccessException {
        ws = new WebSocketFacade(serverUrl, notificationHandler);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redrawGame();
                case "leave" -> leaveGame();
                case "move" -> makeMove(params);
                case "resign" -> resign();
                case "highlight" -> highlightMoves();
                default -> help();
            };
        } catch (DataAccessException | ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String redrawGame() throws ResponseException, DataAccessException {
        return null;
    }

    public String leaveGame(){
        return null;
    }

    public String makeMove(String... params){
        return null;
    }

    public String resign(){
        return null;
    }

    public String highlightMoves(String... params){
        return null;
    }

    public String help() {
        return """
                        redraw - redraws the chess board
                        leave - leaves the game
                        move <ChessMove> - moves a piece from one space to another
                        resign - forfeit the game
                        highlight - highlights all possible moves that can be made
                        help - with possible commands
                        """;
    }
}
