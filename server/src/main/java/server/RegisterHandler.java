package server;

import com.google.gson.Gson;
import model.AuthData;
import model.Error;
import model.UserData;
import service.*;
import spark.Request;
import spark.Response;
import userDataAccess.DataAccessException;

public class RegisterHandler {

    private final UserService userService;

    public RegisterHandler(UserService user){
        userService = user;
    }

    public Object register(Request req, Response res){
        try {
            UserData user = new Gson().fromJson(req.body(), UserData.class);
            AuthData authToken = userService.register(user);
            return new Gson().toJson(authToken);
        } catch (DataAccessException ex){
            new ExceptionHandler(ex, req, res);
            Error exceptionMessage = new Error(ex.getMessage());
            return new Gson().toJson(exceptionMessage);
    }
    }
}
