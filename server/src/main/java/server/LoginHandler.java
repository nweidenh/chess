package server;

import com.google.gson.Gson;
import model.AuthData;
import model.Error;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;
import userDataAccess.DataAccessException;

public class LoginHandler {
    private final UserService userService;

    public LoginHandler(UserService user) {
        userService = user;
    }

    public Object login(Request req, Response res) {
        try {
            UserData user = new Gson().fromJson(req.body(), UserData.class);
            AuthData authToken = userService.login(user);
            return new Gson().toJson(authToken);
        } catch (DataAccessException ex) {
            new ExceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }
}
