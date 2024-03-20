package server;

import spark.*;
import service.*;
import dataAccess.*;

public class Server {
    private final UserService userService;
    private final AuthService authService;
    private final GameService gameService;


    public Server() {
        UserDAO usersDataAccess = null;
        try {
            usersDataAccess = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        AuthDAO authsDataAccess = null;
        try {
            authsDataAccess = new SQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        GameDAO gamesDataAccess = null;
        try {
            gamesDataAccess = new SQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
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
        return new RegisterHandler(userService).register(req, res);
    }

    private Object login(Request req, Response res) {
        return new LoginHandler(userService).login(req, res);
    }

    private Object logout(Request req, Response res) {
        return new LogoutHandler(userService).logout(req, res);
    }

    private Object lstGames(Request req, Response res) {
        return new ListGamesHandler(gameService).lstGames(req, res);
    }

    private Object createGame(Request req, Response res) {
        return new CreateGameHandler(gameService).createGame(req, res);
    }

    private Object joinGame(Request req, Response res) {
        return new JoinGameHandler(gameService).joinGame(req, res);
    }

    private Object clear(Request req, Response res) {
        return new ClearHandler(userService, gameService, authService).clear(req, res);
    }

    public static void main(String[] args) {
        new Server().run(8080);
    }
}
