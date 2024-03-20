package chess.PieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class QueenMovesCalculator implements PieceMovesCalculator{

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> queen = new HashSet<>();
        queen.addAll(new RookMovesCalculator().pieceMoves(board, myPosition));
        queen.addAll(new BishopMovesCalculator().pieceMoves(board, myPosition));
        return queen;
    }
}
