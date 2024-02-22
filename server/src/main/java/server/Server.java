package server;

import spark.*;
import service.*;
import userDataAccess.AuthDAO;
import userDataAccess.MemoryAuthDAO;
import userDataAccess.MemoryUserDAO;
import userDataAccess.UserDAO;
import com.google.gson.Gson;
import model.*;

public class Server {
    private final UserService service;

    public Server() {
        service = new UserService(new MemoryUserDAO(), new MemoryAuthDAO());
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
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
        AuthData authToken = service.register(user);
        res.status(200);
        return new Gson().toJson(authToken);
    }

    private Object login(Request req, Response res) {
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        AuthData authToken = service.login(user);
        res.status(200);
        return new Gson().toJson(authToken);
    }

    private Object logout(Request req, Response res) {
        AuthData auth = new Gson().fromJson(req.body(), AuthData.class);
        service.logout(auth);
        res.status(200);
        return "{}";
    }

    private Object clear(Request req, Response res) {
        service.delete();
        res.status(200);
        return "{}";
    }

    public static void main(String[] args) {
        new Server().run(8080);
    }
}
