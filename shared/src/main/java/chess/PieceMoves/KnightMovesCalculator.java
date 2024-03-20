package chess.PieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class KnightMovesCalculator implements PieceMovesCalculator{

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> knight = new HashSet<>();
        int column = myPosition.getColumn();
        int row = myPosition.getRow();
        //Up 2 Right 1
        if(column > 0 && column < 8 && row > 0 && row < 7){
            ChessPosition endPos = new ChessPosition(row + 2, column + 1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                knight.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                knight.add(myMove);
            }
        }
        //Up 1 Right 2
        if(column > 0 && column < 7 && row > 0 && row < 8){
            ChessPosition endPos = new ChessPosition(row + 1, column +2);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                knight.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                knight.add(myMove);
            }
        }
        //Down 1 Right 2
        if(column > 0 && column < 7 && row > 1 && row < 9){
            ChessPosition endPos = new ChessPosition(row - 1, column + 2);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                knight.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                knight.add(myMove);
            }
        }
        //Down 2 Right 1
        if(column > 0 && column < 8 && row > 2 && row < 9){
            ChessPosition endPos = new ChessPosition(row - 2, column + 1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                knight.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                knight.add(myMove);
            }
        }
        //Down 2 Left 1
        if(column > 1 && column < 9 && row > 2 && row < 9){
            ChessPosition endPos = new ChessPosition(row - 2, column -1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                knight.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                knight.add(myMove);
            }
        }
        //Down 1 Left 2
        if(column > 2 && column < 9 && row > 1 && row < 9){
            ChessPosition endPos = new ChessPosition( row - 1, column - 2);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                knight.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                knight.add(myMove);
            }
        }
        //Up 1 Left 2
        if (column > 2 && column < 9 && row > 0 && row < 8) {
            ChessPosition endPos = new ChessPosition(row + 1, column -2);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if (board.getPiece(endPos) == null) {
                knight.add(myMove);
            } else if (board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                knight.add(myMove);
            }
        }
        //Up 2 Left 1
        if(column > 1 && column < 9 && row > 0 && row < 7){
            ChessPosition endPos = new ChessPosition(row + 2, column - 1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                knight.add(myMove);
            } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                knight.add(myMove);
            }
        }
        return knight;
    }
}
