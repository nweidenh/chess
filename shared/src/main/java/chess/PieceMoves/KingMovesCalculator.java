package chess.PieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class KingMovesCalculator implements PieceMovesCalculator{

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> king = new HashSet<>();
        int column = myPosition.getColumn();
        int row = myPosition.getRow();
        //Up Right
        if(column > 0 && column < 8 && row > 0 && row < 8){
            ChessPosition endPos = new ChessPosition(row + 1, column +1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                king.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                king.add(myMove);
            }
        }
        //Right
        if(column > 0 && column < 8 && row > 0 && row < 9){
            ChessPosition endPos = new ChessPosition(row, column +1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                king.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                king.add(myMove);
            }
        }
        //Down Right
        if(column > 0 && column < 8 && row > 1 && row < 9){
            ChessPosition endPos = new ChessPosition(row - 1, column +1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                king.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                king.add(myMove);
            }
        }
        //Down
        if(column > 0 && column < 9 && row > 1 && row < 9){
            ChessPosition endPos = new ChessPosition(row - 1, column);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                king.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                king.add(myMove);
            }
        }
        //Down Left
        if(column > 1 && column < 9 && row > 1 && row < 9){
            ChessPosition endPos = new ChessPosition(row - 1, column -1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                king.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                king.add(myMove);
            }
        }
        //Left
        if(column > 1 && column < 9 && row > 0 && row < 9){
            ChessPosition endPos = new ChessPosition( row, column - 1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                king.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                king.add(myMove);
            }
        }
        //Up Left
        if (column > 1 && column < 9 && row > 0 && row < 8) {
            ChessPosition endPos = new ChessPosition(row + 1, column -1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if (board.getPiece(endPos) == null) {
                king.add(myMove);
            } else if (board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                king.add(myMove);
            }
        }
        //Up
        if(column > 0 && column < 9 && row > 0 && row < 8){
            ChessPosition endPos = new ChessPosition(row + 1, column);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                king.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                king.add(myMove);
            }
        }
        return king;
    }
}
