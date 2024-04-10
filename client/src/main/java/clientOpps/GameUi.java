package clientOpps;

import chess.*;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class GameUi {
    private final String serverUrl;
    private WebSocketFacade ws;
    private final NotificationHandler notificationHandler;
    public String authToken = null;
    public Integer gameID = null;
    Scanner scanner;

    public GameUi(String serverUrl, NotificationHandler notificationHandler) {
        ws = new WebSocketFacade(serverUrl, notificationHandler);
        this.serverUrl = serverUrl;
        this.notificationHandler = notificationHandler;
        this.scanner = new Scanner(System.in);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redrawGame(params);
                case "leave" -> leaveGame(params);
                case "move" -> makeMove(params);
                case "resign" -> resign(params);
                case "highlight" -> highlightMoves(params);
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String redrawGame(String... params) throws ResponseException, IOException {
        if (params.length == 0) {
            ws.redrawBoard(gameID, authToken);
            return "";
        }
        else {
            throw new ResponseException(400, "Expected a 1 word command");
        }
    }

    public String leaveGame(String... params) throws IOException, ResponseException {
        if (params.length == 0) {
            ws.leaveGame(gameID, authToken);
            return "You left the game";
        } else {
            throw new ResponseException(400, "Expected a 1 word command");
        }
    }

    public String makeMove(String... params) throws IOException, ResponseException {
        if (params.length == 0) {
            System.out.println("Enter the space of the piece you want to move in this format: rowNumber columnLetter (ex 7 a)");
            ChessPosition start = makePositionFromInput();
            System.out.println("Enter the space that you want to move to in this format: rowNumber columnLetter (ex 6 a)");
            ChessPosition end = makePositionFromInput();
            ws.makeMove(gameID, authToken, new ChessMove(start, end, null));
            return "";
        } else {
            throw new ResponseException(400, "Expected a 1 word command");
        }
    }

    private ChessPosition makePositionFromInput() throws ResponseException {
        int row = 0;
        int col = 0;
        String move = scanner.nextLine();
        var tokens = move.toUpperCase().split(" ");
        if(tokens.length != 2){
            throw new ResponseException(400, "Expected 1 number, then a space, then 1 letter");
        }
        row = Integer.parseInt(tokens[0]);
        if (row > 8){
            throw new ResponseException(400, "Number is not 1-8");
        }
        col = tokens[1].charAt(0) - 'A' + 1;
        if (col > 8){
            throw new ResponseException(400, "Letter is not on a-h");
        }
        return new ChessPosition(row, col);
    }

    public String resign(String... params) throws IOException, ResponseException {
        if (params.length == 0){
        System.out.println("Are you sure you want to resign? (yes or no)");
        String decision = scanner.nextLine();
        if(decision.equalsIgnoreCase("yes")){
            ws.resignGame(gameID, authToken);
            return "";
        } else {
            return "You did not resign from the game";
        }}else {
            throw new ResponseException(400, "Expected a 1 word command");
        }
    }

    public String highlightMoves(String... params) throws IOException, ResponseException {
        if (params.length == 0) {
            System.out.println("Enter the space of the piece you want to highlight all potential moves of: rowNumber columnLetter (ex 7 a)");
            ChessPosition start = makePositionFromInput();
            ws.highlightMove(gameID, authToken, start);
            return "";
        } else {
            throw new ResponseException(400, "Expected a 1 word command");
        }
    }

    public String help() {
        return """
                        redraw - redraws the chess board
                        leave - leaves the game
                        move - moves a piece from one space to another
                        resign - forfeit the game
                        highlight - highlights all possible moves that can be made
                        help - with possible commands""";
    }
}
