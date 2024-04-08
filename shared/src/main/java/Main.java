import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class Main {

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        ChessGame game = new ChessGame();
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        game.setBoard(board);
        System.out.println(game.board);
        ChessPosition test = new ChessPosition(2,1);
        System.out.println(game.board.getPiece(test));
        System.out.println(game.findHighlight(new ChessPosition(8,1)));
    }
}
