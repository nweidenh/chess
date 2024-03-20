package chess;

import java.util.Arrays;
import ui2.EscapeSequences2;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    public ChessPiece [][] boardSquares;

    public ChessBoard() {
        boardSquares = new ChessPiece[8][8];
    }

    public ChessBoard(ChessBoard copy){
        int rows = copy.boardSquares.length;
        int columns = copy.boardSquares[0].length;

        this.boardSquares = new ChessPiece[rows][columns];

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                this.boardSquares[i][j] = copy.boardSquares[i][j];
            }
        }
    }
    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        boardSquares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    public void removePiece(ChessPosition position){
        boardSquares[position.getRow()-1][position.getColumn()-1] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return boardSquares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.addPiece(new ChessPosition(1,1), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        boardSquares[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        boardSquares[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        boardSquares[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        boardSquares[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        boardSquares[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        boardSquares[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        boardSquares[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        boardSquares[1][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boardSquares[1][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boardSquares[1][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boardSquares[1][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boardSquares[1][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boardSquares[1][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boardSquares[1][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boardSquares[1][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        boardSquares[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        boardSquares[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        boardSquares[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        boardSquares[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        boardSquares[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        boardSquares[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        boardSquares[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        boardSquares[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        boardSquares[6][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        boardSquares[6][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        boardSquares[6][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        boardSquares[6][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        boardSquares[6][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        boardSquares[6][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        boardSquares[6][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        boardSquares[6][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    }

    public String toString(){
        System.out.println(EscapeSequences2.SET_BG_COLOR_BLACK);
        String retString = "";
        retString += "\n \u2001\u2005\u200Aa\u2001\u2005\u200Ab\u2001\u2005\u200Ac\u2001\u2005\u200Ad\u2001\u2005\u200Ae\u2001\u2005\u200Af\u2001\u2005\u200Ag\u2001\u2005\u200Ah\u2001\u2005\u200A\n";
        int rowNum = 8;
        for(int col = 0; col < boardSquares.length; col++) {
            retString = retString + rowNum + " |";
            for (int row = 0; row < boardSquares.length; row++) {
                if (boardSquares[col][row] == null){
                    retString += "\u2001\u2005\u200A|";
                }else if(boardSquares[col][row].getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.PAWN) {
                        retString += EscapeSequences2.WHITE_PAWN + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.QUEEN) {
                        retString += EscapeSequences2.WHITE_QUEEN + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.KING) {
                        retString += EscapeSequences2.WHITE_KING + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        retString += EscapeSequences2.WHITE_KNIGHT + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.ROOK) {
                        retString += EscapeSequences2.WHITE_ROOK + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.BISHOP) {
                        retString += EscapeSequences2.WHITE_BISHOP + "|";
                    }
                }else{
                    if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.PAWN) {
                        retString += EscapeSequences2.BLACK_PAWN + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.QUEEN) {
                        retString += EscapeSequences2.BLACK_QUEEN + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.KING) {
                        retString += EscapeSequences2.BLACK_KING + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        retString += EscapeSequences2.BLACK_KNIGHT + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.ROOK) {
                        retString += EscapeSequences2.BLACK_ROOK + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.BISHOP) {
                        retString += EscapeSequences2.BLACK_BISHOP + "|";
                    }
                }
            }
            retString = retString + " " + rowNum + "\n";
            rowNum -= 1;
        } retString += " \u2001\u2005\u200Aa\u2001\u2005\u200Ab\u2001\u2005\u200Ac\u2001\u2005\u200Ad\u2001\u2005\u200Ae\u2001\u2005\u200Af\u2001\u2005\u200Ag\u2001\u2005\u200Ah\u2001\u2005\u200A";;
        return retString;
    }
    public String toStringFlipped(){
        System.out.println(EscapeSequences2.SET_BG_COLOR_BLACK);
        String retString = "";
        retString += "\n \u2001\u2005\u200Ah\u2001\u2005\u200Ag\u2001\u2005\u200Af\u2001\u2005\u200Ae\u2001\u2005\u200Ad\u2001\u2005\u200Ac\u2001\u2005\u200Ab\u2001\u2005\u200Aa\u2001\u2005\u200A\n";
        int rowNum = 1;
        for(int col = boardSquares.length - 1; col > -1; col--) {
            retString = retString + rowNum + " |";
            for (int row = boardSquares.length - 1; row > -1; row--) {
                if (boardSquares[col][row] == null){
                    retString += "\u2001\u2005\u200A|";
                }else if(boardSquares[col][row].getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.PAWN) {
                        retString += EscapeSequences2.WHITE_PAWN + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.QUEEN) {
                        retString += EscapeSequences2.WHITE_QUEEN + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.KING) {
                        retString += EscapeSequences2.WHITE_KING + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        retString += EscapeSequences2.WHITE_KNIGHT + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.ROOK) {
                        retString += EscapeSequences2.WHITE_ROOK + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.BISHOP) {
                        retString += EscapeSequences2.WHITE_BISHOP + "|";
                    }
                }else{
                    if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.PAWN) {
                        retString += EscapeSequences2.BLACK_PAWN + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.QUEEN) {
                        retString += EscapeSequences2.BLACK_QUEEN + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.KING) {
                        retString += EscapeSequences2.BLACK_KING + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        retString += EscapeSequences2.BLACK_KNIGHT + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.ROOK) {
                        retString += EscapeSequences2.BLACK_ROOK + "|";
                    } else if (boardSquares[col][row].getPieceType() == ChessPiece.PieceType.BISHOP) {
                        retString += EscapeSequences2.BLACK_BISHOP + "|";
                    }
                }
            }
            retString = retString + " " + rowNum + "\n";
            rowNum += 1;
        } retString += " \u2001\u2005\u200Ah\u2001\u2005\u200Ag\u2001\u2005\u200Af\u2001\u2005\u200Ae\u2001\u2005\u200Ad\u2001\u2005\u200Ac\u2001\u2005\u200Ab\u2001\u2005\u200Aa\u2001\u2005\u200A";;
        return retString;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;
        return Arrays.deepEquals(boardSquares, that.boardSquares);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(boardSquares);
    }
}
