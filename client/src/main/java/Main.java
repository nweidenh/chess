import clientOpps.Repl;
import chess.*;
import server.Server;

public class Main {
    public static Server server;
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        var serverUrl = "http://localhost:" + port;
        new Repl(serverUrl).run();
    }
}