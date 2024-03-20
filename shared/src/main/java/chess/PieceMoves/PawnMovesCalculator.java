package chess.PieceMoves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class PawnMovesCalculator implements PieceMovesCalculator{

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> pawn = new HashSet<>();
        int column = myPosition.getColumn();
        int row = myPosition.getRow();
        //Up 1
        if(column > 0 && column < 9 && row > 0 && row < 8 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            ChessPosition endPos = new ChessPosition(row + 1, column);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                if(endPos.getRow() == 8){
                    pawn.addAll(pawnPromotionLst(myPosition, endPos));
                }else {
                    pawn.add(myMove);
                }
            }
        }
        //Up 2
        if(row == 2 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            ChessPosition endPos = new ChessPosition(row + 2, column);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            ChessPosition checkPos = new ChessPosition(row + 1, column);
            if(board.getPiece(endPos) == null && board.getPiece(checkPos) == null){
                pawn.add(myMove);
            }
        }
        //Down 1
        if(column > 0 && column < 9 && row > 1 && row < 9 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            ChessPosition endPos = new ChessPosition(row - 1, column);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                if(endPos.getRow() == 1){
                    pawn.addAll(pawnPromotionLst(myPosition, endPos));
                }else {
                    pawn.add(myMove);
                }
            }
        }
        //Down 2
        if(row == 7 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            ChessPosition endPos = new ChessPosition(row - 2, column);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            ChessPosition checkPos = new ChessPosition(row - 1, column);
            if(board.getPiece(endPos) == null && board.getPiece(checkPos) == null){
                pawn.add(myMove);
            }
        }
        //Up Left
        if(column > 1 && column < 9 && row > 0 && row < 8 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            ChessPosition endPos = new ChessPosition(row + 1, column - 1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) != null && board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                if(endPos.getRow() == 8){
                    pawn.addAll(pawnPromotionLst(myPosition, endPos));
                }else {
                    pawn.add(myMove);
                }
            }
        }
        //Up Right
        if(column > 0 && column < 8 && row > 0 && row < 8 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            ChessPosition endPos = new ChessPosition(row + 1, column + 1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) != null && board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                if(endPos.getRow() == 8){
                    pawn.addAll(pawnPromotionLst(myPosition, endPos));
                }else {
                    pawn.add(myMove);
                }
            }
        }
        //Down Left
        if(column > 1 && column < 9 && row > 1 && row < 9 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            ChessPosition endPos = new ChessPosition(row - 1, column - 1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) != null && board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                if(endPos.getRow() == 1){
                    pawn.addAll(pawnPromotionLst(myPosition, endPos));
                }else {
                    pawn.add(myMove);
                }
            }
        }
        //Down Right
        if(column > 0 && column < 8 && row > 1 && row < 9 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            ChessPosition endPos = new ChessPosition(row - 1, column + 1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) != null && board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                if(endPos.getRow() == 1){
                    pawn.addAll(pawnPromotionLst(myPosition, endPos));
                }else {
                    pawn.add(myMove);
                }
            }
        }
        return pawn;
    }

    private ArrayList<ChessMove> pawnPromotionLst(ChessPosition start, ChessPosition endPos){
        ArrayList<ChessMove> myMoves = new ArrayList<>();
        for(ChessPiece.PieceType piece: ChessPiece.PieceType.values()){
            if(piece== ChessPiece.PieceType.PAWN || piece == ChessPiece.PieceType.KING){
                continue;
            } else{
                ChessMove promoMove = new ChessMove(start, endPos, piece);
                myMoves.add(promoMove);
            }
        }
        return myMoves;
    }
}


