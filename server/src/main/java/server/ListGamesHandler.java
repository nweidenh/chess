package server;

import com.google.gson.Gson;
import model.Error;
import service.GameService;
import spark.Request;
import spark.Response;
import dataAccess.DataAccessException;

import java.util.Map;

public class ListGamesHandler {
    private final GameService gameService;

    public ListGamesHandler(GameService game) {
        gameService = game;
    }

    public Object lstGames(Request req, Response res) {
        try{
            String authToken = new Gson().fromJson(req.headers("authorization"), String.class);
            var list = gameService.lstGames(authToken).toArray();
            return new Gson().toJson(Map.of("games",list));
        } catch (DataAccessException ex){
            new ExceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }
}
