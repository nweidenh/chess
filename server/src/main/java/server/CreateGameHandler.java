package server;

import com.google.gson.Gson;
import model.Error;
import model.GameData;
import model.createGameResponse;
import service.GameService;
import spark.Request;
import spark.Response;
import userDataAccess.DataAccessException;

import java.util.Map;

public class CreateGameHandler {
    private final GameService gameService;

    public CreateGameHandler(GameService game) {
        gameService = game;
    }

    public Object createGame(Request req, Response res) {
        try {
            String authToken = new Gson().fromJson(req.headers("authorization"), String.class);
            GameData gameName = new Gson().fromJson(req.body(), GameData.class);
            createGameResponse createdGameID = gameService.createGame(authToken, gameName.gameName());
            return new Gson().toJson(createdGameID);
        } catch (DataAccessException ex) {
            new ExceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }
}
