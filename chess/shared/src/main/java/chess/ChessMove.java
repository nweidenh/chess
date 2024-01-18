package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    ChessPosition start;
    ChessPosition end;
    ChessPiece.PieceType promotion;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        start = startPosition;
        end = endPosition;
        promotion = promotionPiece;

    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        //throw new RuntimeException("Not implemented");
        return start;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        //throw new RuntimeException("Not implemented");
        return end;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotion;
    }

    @Override
    public String toString() {
        String pString = "[" + getEndPosition().getRow() + "," + getEndPosition().getColumn() + "]";
        //return String.format("[%d,%d] -> [%d,%d], (%S)", getStartPosition().getRow(), getStartPosition().getColumn(), getEndPosition().getRow(), getEndPosition().getColumn(), getPromotionPiece());
//        if(getPromotionPiece()!= null)
//            pString += " (" + getPromotionPiece().toString() + ")";
        return pString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessMove chessMove)) return false;
        return Objects.equals(start, chessMove.start) && Objects.equals(end, chessMove.end) && promotion == chessMove.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, promotion);
    }
}
