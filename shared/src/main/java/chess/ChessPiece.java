package chess;

import chess.PieceMoves.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pColor;
    ChessPiece.PieceType pType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        pColor = pieceColor;
        pType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if(board.getPiece(myPosition).getPieceType() == PieceType.BISHOP){
            return new BishopMovesCalculator().pieceMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.ROOK){
            return new RookMovesCalculator().pieceMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.QUEEN){
            return new QueenMovesCalculator().pieceMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.KING){
            return new KingMovesCalculator().pieceMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT){
            return new KnightMovesCalculator().pieceMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.PAWN){
            return new PawnMovesCalculator().pieceMoves(board, myPosition);
        }return null;
    }

    @Override
    public String toString() {
        return "(" + pColor + "," + pType + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pColor == that.pColor && pType == that.pType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pColor, pType);
    }
}

