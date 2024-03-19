import chess.ChessGame;
import model.GameData;
import model.JoinGameRequest;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;

public class PostLogin {

    private final ServerFacade server;
    private final String serverUrl;
    private Map<Integer, GameData> gamesListed;

    public PostLogin(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
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
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
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
            var gameID = server.createGame(game);
            return "you created game " + params[0] + " with gameID " + gameID;
        }
        throw new ResponseException(400, "Expected: <NAME>");
    }
    public String joinGame(String... params) throws ResponseException {
        if (params.length == 2) {
            JoinGameRequest gameToJoin = new JoinGameRequest(params[1], Integer.parseInt(params[0]));
            server.joinGame(gameToJoin);
            return "you joined game " + params[0];
        } if (params.length == 1){
            JoinGameRequest gameToJoin = new JoinGameRequest(null, Integer.parseInt(params[0]));
            server.joinGame(gameToJoin);
            return "you joined game " + params[0];
        }
        throw new ResponseException(400, "Expected: listed game number");
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
                        quit - playing chess
                        help - with possible commands
                        """;
    }
}
