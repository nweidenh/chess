import chess.*;

public class Main {

    public static void main(String[] args) throws InvalidMoveException {
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
