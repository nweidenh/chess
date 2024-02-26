package server;

import spark.*;
import service.*;
import userDataAccess.*;
import com.google.gson.Gson;
import model.*;
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

        Spark.awaitInitialization();
        return Spark.port();
    }


    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object register(Request req, Response res) {
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        AuthData authToken = userService.register(user);
        return new Gson().toJson(authToken);
    }

    private Object login(Request req, Response res) {
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        AuthData authToken = userService.login(user);
        return new Gson().toJson(authToken);
    }

    private Object logout(Request req, Response res) {
        String authToken = new Gson().fromJson(req.headers("Authentication"), String.class);
        userService.logout(authToken);
        return "{}";
    }

    private Object lstGames(Request req, Response res) {
        String authToken = new Gson().fromJson(req.headers("Authentication"), String.class);
        var list = gameService.lstGames(authToken).toArray();
        return new Gson().toJson(Map.of("games",list));
    }

    private Object createGame(Request req, Response res) {
        String authToken = new Gson().fromJson(req.headers("Authentication"), String.class);
        GameData gameName = new Gson().fromJson(req.body(), GameData.class);
        createGameResponse createdGameID = gameService.createGame(authToken, gameName.gameName());
        return new Gson().toJson(createdGameID);
    }

    private Object joinGame(Request req, Response res) {
        String auth = new Gson().fromJson(req.headers("Authentication"), String.class);
        joinGameRequest game = new Gson().fromJson(req.body(), joinGameRequest.class);
        gameService.joinGame(auth, game);
        return "{}";
    }

    private Object clear(Request req, Response res) {
        userService.deleteAll();
        gameService.deleteAll();
        authService.deleteAll();
        return "{}";
    }



    public static void main(String[] args) {
        new Server().run(8080);
    }
}
