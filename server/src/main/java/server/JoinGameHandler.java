package server;

import com.google.gson.Gson;
import model.Error;
import model.JoinGameRequest;
import service.GameService;
import spark.Request;
import spark.Response;
import dataAccess.DataAccessException;

public class JoinGameHandler {
    private final GameService gameService;

    public JoinGameHandler(GameService game) {
        gameService = game;
    }

    public Object joinGame(Request req, Response res) {
        try {
            if (containsWhitespace(req.headers("authorization"))) {
                throw new DataAccessException(401, "Error: unauthorized");
            }
            String auth = new Gson().fromJson(req.headers("authorization"), String.class);
            JoinGameRequest game = new Gson().fromJson(req.body(), JoinGameRequest.class);
            gameService.joinGame(auth, game);
            return "{}";
        } catch (DataAccessException ex) {
            new ExceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }

    public static boolean containsWhitespace(String str) {
        return str != null && str.matches(".*\\s.*");
    }
}
