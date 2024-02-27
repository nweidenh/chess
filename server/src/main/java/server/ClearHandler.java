package server;

import com.google.gson.Gson;
import model.Error;
import service.AuthService;
import service.GameService;
import service.UserService;
import spark.Request;
import spark.Response;
import userDataAccess.DataAccessException;

public class ClearHandler {
    private final UserService userService;
    private final AuthService authService;
    private final GameService gameService;

    public ClearHandler(UserService user, GameService game, AuthService auth) {
        userService = user;
        gameService = game;
        authService = auth;
    }

    public Object clear(Request req, Response res) {
        try{
            userService.deleteAll();
            gameService.deleteAll();
            authService.deleteAll();
            return "{}";
        } catch (DataAccessException ex){
            new ExceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }
}
