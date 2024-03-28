import clientOpps.Repl;
import chess.*;
import dataAccess.DataAccessException;
import server.Server;

public class Main {
    public static Server server;
    public static void main(String[] args) throws DataAccessException {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
//        server = new Server();
//        var port = server.run(8080);
//        System.out.println("Started test HTTP server on " + port);
//        var serverUrl = "http://localhost:" + port;
        new Repl(serverUrl).run();
    }
}