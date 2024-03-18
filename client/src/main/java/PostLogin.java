import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.util.Arrays;
import com.google.gson.Gson;

public class PostLogin {

    private String username = null;
    private String password = null;
    private String email = null;
    private final ServerFacade server;
    private final String serverUrl;

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
                case "join" -> joinGame();
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
        var gson = new Gson();
        for (var game : games){
            result.append(gson.toJson(game)).append('\n');
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
    public String joinGame() throws ResponseException {
        return null;
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
