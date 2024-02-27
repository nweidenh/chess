package server;

import spark.*;
import service.*;
import userDataAccess.*;
import com.google.gson.Gson;
import model.*;

import javax.xml.crypto.Data;
import java.util.Map;

public class Server {
    private final UserDAO usersDataAccess = new MemoryUserDAO();
    private final AuthDAO authsDataAccess = new MemoryAuthDAO();
    private final GameDAO gamesDataAccess = new MemoryGameDAO();
    private final UserService userService;
    private final AuthService authService;
    private final GameService gameService;


    public Server() {
        userService = new UserService(usersDataAccess, authsDataAccess, gamesDataAccess);
        authService = new AuthService(usersDataAccess, authsDataAccess, gamesDataAccess);
        gameService = new GameService(usersDataAccess, authsDataAccess, gamesDataAccess);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::lstGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
        Spark.delete("/db", this::clear);
        Spark.exception(DataAccessException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private void exceptionHandler(DataAccessException ex, Request req, Response res) {
        res.status(ex.StatusCode());
    }

    private Object register(Request req, Response res){
        try {
            UserData user = new Gson().fromJson(req.body(), UserData.class);
            AuthData authToken = userService.register(user);
            return new Gson().toJson(authToken);
        } catch (DataAccessException ex){
            exceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }

    private Object login(Request req, Response res) {
        try {
            UserData user = new Gson().fromJson(req.body(), UserData.class);
            AuthData authToken = userService.login(user);
            return new Gson().toJson(authToken);
        } catch (DataAccessException ex){
            exceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }

    private Object logout(Request req, Response res) {
        try {
            String authToken = new Gson().fromJson(req.headers("authorization"), String.class);
            userService.logout(authToken);
            return "{}";
        } catch (DataAccessException ex){
            exceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }

    private Object lstGames(Request req, Response res) {
        try{
            String authToken = new Gson().fromJson(req.headers("authorization"), String.class);
            var list = gameService.lstGames(authToken).toArray();
            return new Gson().toJson(Map.of("games",list));
        } catch (DataAccessException ex){
            exceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }

    private Object createGame(Request req, Response res) {
        try{
            String authToken = new Gson().fromJson(req.headers("authorization"), String.class);
            GameData gameName = new Gson().fromJson(req.body(), GameData.class);
            createGameResponse createdGameID = gameService.createGame(authToken, gameName.gameName());
            return new Gson().toJson(createdGameID);
        } catch (DataAccessException ex){
            exceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }

    }

    private Object joinGame(Request req, Response res) {
        try{
            String auth = new Gson().fromJson(req.headers("authorization"), String.class);
            joinGameRequest game = new Gson().fromJson(req.body(), joinGameRequest.class);
            gameService.joinGame(auth, game);
            return "{}";
        } catch (DataAccessException ex){
            exceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }

    private Object clear(Request req, Response res) {
        try{
            userService.deleteAll();
            gameService.deleteAll();
            authService.deleteAll();
            return "{}";
        } catch (DataAccessException ex){
            exceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }


    public static void main(String[] args) {
        new Server().run(8080);
    }
}
