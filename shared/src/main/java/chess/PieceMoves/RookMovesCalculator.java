package chess.PieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class RookMovesCalculator implements PieceMovesCalculator{
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> rook = new HashSet<>();
        int column = myPosition.getColumn();
        int row = myPosition.getRow();
        //Up
        for(int i = myPosition.getRow(); i < 8; i++){
            if(column > 0 && column < 9){
                ChessPosition endPos = new ChessPosition(i + 1, column);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if(board.getPiece(endPos) == null){
                    rook.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    rook.add(myMove);
                    break;
                } else{
                    break;
                }
            }
        }
        //Right
        for(int i = myPosition.getColumn(); i < 8; i++){
            if(row > 0 && row < 9){
                ChessPosition endPos = new ChessPosition( row, i + 1);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if(board.getPiece(endPos) == null){
                    rook.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    rook.add(myMove);
                    break;
                } else{
                    break;
                }
            }
        }
        //Down
        for(int i = myPosition.getRow(); i > 1; i--) {
            if (column > 0 && column < 9) {
                ChessPosition endPos = new ChessPosition(i - 1, column);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    rook.add(myMove);
                } else if (board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    rook.add(myMove);
                    break;
                } else {
                    break;
                }
            }
        }
        //Left
        for(int i = myPosition.getColumn(); i > 1; i--){
            if(row > 0 && row< 9){
                ChessPosition endPos = new ChessPosition(row, i - 1);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if(board.getPiece(endPos) == null){
                    rook.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    rook.add(myMove);
                    break;
                } else{
                    break;
                }
            }
        }
        return rook;
    }
}
