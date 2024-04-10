package clientOpps;

import chess.ChessGame;
import model.GameData;
import model.JoinGameRequest;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PostLogin {

    private final ServerFacade server;
    private final String serverUrl;
    private final Map<Integer, GameData> gamesListed = new HashMap<>();
    private WebSocketFacade ws;
    private final NotificationHandler notificationHandler;
    public Boolean inWs = false;
    public String authToken = null;
    public Integer gameID;

    public PostLogin(String serverUrl, NotificationHandler notificationHandler) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "list" -> listGames();
                case "logout" -> logout();
                case "join" -> joinGame(params);
                case "create" -> createGame(params);
                case "observe" -> observeGame(params);
                default -> help();
            };
        } catch (ResponseException | IOException ex) {
            return ex.getMessage();
        }
    }

    public String listGames() throws ResponseException {
        var games = server.listGames();
        var result = new StringBuffer();
        var i = 1;
        for (var game : games){
            result.append("Game Number ").append(i).append( " -> Name: ").append(game.gameName());
            if (game.whiteUsername() != null){
                result.append(" White Player- ").append(game.whiteUsername());
            }if (game.blackUsername() != null){
                result.append(" Black Player- ").append(game.blackUsername());
            }if (game.whiteUsername() == null && game.blackUsername() == null){
                result.append(", There are no users playing this game");
            }if (games.length > i){
                result.append('\n');
            } gamesListed.put(i, game);
            i += 1;
        }
        return result.toString();
    }

    public String createGame(String... params) throws ResponseException {
        if (params.length <= 1) {
            GameData game = new GameData(0, null, null, params[0], null);
            server.createGame(game);
            return "You created game " + params[0];
        }
        throw new ResponseException(400, "Expected: <NAME>");
    }

    public String joinGame(String... params) throws ResponseException, IOException {
        if (params.length == 2) {
            GameData joiningThisGame = gamesListed.get(Integer.parseInt(params[0]));
            JoinGameRequest gameToJoin = new JoinGameRequest(params[1], joiningThisGame.gameID());
            server.joinGame(gameToJoin);
            ws = new WebSocketFacade(serverUrl, notificationHandler);
            ws.joinGame(gameToJoin, authToken);
            inWs = true;
            gameID = joiningThisGame.gameID();
            return "You joined game " + params[0]; // + "\nThis is the current state of the game" + joiningThisGame.game().getBoard().toString() + "\nand flipped it is" + joiningThisGame.game().getBoard().toStringFlipped();
        } if (params.length == 1){
            return observeGame(params);
        }
        throw new ResponseException(400, "Expected: game number and a desired color");
    }

    public String observeGame(String... params) throws ResponseException, IOException {
        if (params.length == 1){
            GameData joiningThisGame = gamesListed.get(Integer.parseInt(params[0]));
            JoinGameRequest gameToJoin = new JoinGameRequest(null, joiningThisGame.gameID());
            server.joinGame(gameToJoin);
            ws = new WebSocketFacade(serverUrl, notificationHandler);
            ws.joinObvGame(gameToJoin, authToken);
            inWs = true;
            gameID = joiningThisGame.gameID();
            return "You are observing game " + params[0]; //+ "\nThis is the current state of the game" + joiningThisGame.game().getBoard().toString() + "\nand flipped it is" + joiningThisGame.game().getBoard().toStringFlipped();
        }
        throw new ResponseException(400, "Expected: game number");
    }

    public String logout() throws ResponseException {
        server.logout();
        return "You logged out";
    }

    public String help() {
        return """
                        create <NAME> - a game
                        list - games
                        join <ID> [WHITE|BLACK|<empty>] - a game
                        observe <ID> - a game
                        logout - when you are done
                        help - with possible commands
                        """;
    }
}
