package chess.PieceMoves;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.Collection;
import java.util.HashSet;

public class BishopMovesCalculator implements PieceMovesCalculator{

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> bishop = new HashSet<>();
        int column = myPosition.getColumn();
        //Up Right
        for(int i = myPosition.getRow(); i < 8; i++){
            if(column > 0 && column < 8){
                ChessPosition endPos = new ChessPosition(i + 1, column +1);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if(board.getPiece(endPos) == null){
                    bishop.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    bishop.add(myMove);
                    break;
                } else{
                    break;
                }
            } column++;
        } column = myPosition.getColumn();
        //Down Right
        for(int i = myPosition.getRow(); i > 1; i--){
            if(column > 0 && column < 8){
                ChessPosition endPos = new ChessPosition(i - 1, column +1);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if(board.getPiece(endPos) == null){
                    bishop.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    bishop.add(myMove);
                    break;
                } else{
                    break;
                }
            } column++;
        }column = myPosition.getColumn();
        //Down Left
        for(int i = myPosition.getRow(); i > 1; i--){
            if(column > 1 && column < 9){
                ChessPosition endPos = new ChessPosition(i - 1, column -1);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if(board.getPiece(endPos) == null){
                    bishop.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    bishop.add(myMove);
                    break;
                } else{
                    break;
                }
            } column--;
        }column = myPosition.getColumn();
        //Up Left
        for(int i = myPosition.getRow(); i < 8; i++){
            if(column > 1 && column < 9){
                ChessPosition endPos = new ChessPosition(i + 1, column -1);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if(board.getPiece(endPos) == null){
                    bishop.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    bishop.add(myMove);
                    break;
                } else{
                    break;
                }
            } column--;
        }
        return bishop;
    }
}