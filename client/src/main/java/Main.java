import clientOpps.Repl;
import chess.*;
import dataAccess.DataAccessException;
import server.Server;

public class Main {
    public static Server server;
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        new Repl(serverUrl).run();
    }
}