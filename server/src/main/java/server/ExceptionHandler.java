package server;

import spark.Request;
import spark.Response;
import dataAccess.DataAccessException;

public class ExceptionHandler {
    public ExceptionHandler(DataAccessException ex, Request req, Response res) {
        res.status(ex.statusCode());
    }
}
