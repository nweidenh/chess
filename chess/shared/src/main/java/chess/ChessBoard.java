package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece [][] BoardSquares = new ChessPiece[8][8];

    public ChessBoard() {

        //ChessPiece [][] Board = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        /* throw new RuntimeException("Not implemented") */
        BoardSquares[position.getRow()-1][position.getColumn()-1] = piece;

    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        //throw new RuntimeException("Not implemented")
        return BoardSquares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //throw new RuntimeException("Not implemented");
        BoardSquares[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    }

    public String toString(){
        String retString = "";
        for(int col = 0; col < BoardSquares.length; col++)
            retString += Arrays.deepToString(BoardSquares) + "/n";
        return retString;
    }

    public static void Main(){
        ChessBoard testBoard = new ChessBoard();
        System.out.println(testBoard.toString());
    }
}
