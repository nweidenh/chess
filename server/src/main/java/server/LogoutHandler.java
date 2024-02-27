package server;

import com.google.gson.Gson;
import model.Error;
import service.UserService;
import spark.Request;
import spark.Response;
import userDataAccess.DataAccessException;

public class LogoutHandler {

    private final UserService userService;

    public LogoutHandler(UserService user) {
        userService = user;
    }

    public Object logout(Request req, Response res) {
        try {
            String authToken = new Gson().fromJson(req.headers("authorization"), String.class);
            userService.logout(authToken);
            return "{}";
        } catch (DataAccessException ex){
            new ExceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
        }
    }
}

