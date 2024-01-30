package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
            return BishopMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.ROOK){
            return RookMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.QUEEN){
            return QueenMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.KING){
            return KingMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT){
            return KnightMoves(board, myPosition);
        }if(board.getPiece(myPosition).getPieceType() == PieceType.PAWN){
            return PawnMoves(board, myPosition);
        }return null;
    }

    private Collection<ChessMove> BishopMoves(ChessBoard board, ChessPosition myPosition){
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

    private Collection<ChessMove> RookMoves(ChessBoard board, ChessPosition myPosition){
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

    private Collection<ChessMove> QueenMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> queen = new HashSet<>();
        int column = myPosition.getColumn();
        //Up Right
        for(int i = myPosition.getRow(); i < 8; i++){
            if(column > 0 && column < 8){
                ChessPosition endPos = new ChessPosition(i + 1, column +1);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if(board.getPiece(endPos) == null){
                    queen.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    queen.add(myMove);
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
                    queen.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    queen.add(myMove);
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
                    queen.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    queen.add(myMove);
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
                    queen.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    queen.add(myMove);
                    break;
                } else{
                    break;
                }
            } column--;
        }int row = myPosition.getRow();
        column = myPosition.getColumn();
        //Up
        for(int i = myPosition.getRow(); i < 8; i++){
            if(column > 0 && column < 9){
                ChessPosition endPos = new ChessPosition(i + 1, column);
                ChessMove myMove = new ChessMove(myPosition, endPos, null);
                if(board.getPiece(endPos) == null){
                    queen.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    queen.add(myMove);
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
                    queen.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    queen.add(myMove);
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
                    queen.add(myMove);
                } else if (board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    queen.add(myMove);
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
                    queen.add(myMove);
                } else if(board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                    queen.add(myMove);
                    break;
                } else{
                    break;
                }
            }
        }
        return queen;
    }

    private Collection<ChessMove> KingMoves(ChessBoard board, ChessPosition myPosition){
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

    private Collection<ChessMove> KnightMoves(ChessBoard board, ChessPosition myPosition){
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

    private Collection<ChessMove> PawnMoves(ChessBoard board, ChessPosition myPosition){
        HashSet<ChessMove> pawn = new HashSet<>();
        int column = myPosition.getColumn();
        int row = myPosition.getRow();
        //Up 1
        if(column > 0 && column < 9 && row > 0 && row < 8 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            ChessPosition endPos = new ChessPosition(row + 1, column);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) == null){
                if(endPos.getRow() == 8){
                    pawn.addAll(PawnPromotionLst(myPosition, endPos));
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
                    pawn.addAll(PawnPromotionLst(myPosition, endPos));
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
                    pawn.addAll(PawnPromotionLst(myPosition, endPos));
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
                    pawn.addAll(PawnPromotionLst(myPosition, endPos));
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
                    pawn.addAll(PawnPromotionLst(myPosition, endPos));
                }else {
                    pawn.add(myMove);
                }
            }
        }
        //Down Right
        if(column > 0 && column < 9 && row > 1 && row < 9 && board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            ChessPosition endPos = new ChessPosition(row - 1, column + 1);
            ChessMove myMove = new ChessMove(myPosition, endPos, null);
            if(board.getPiece(endPos) != null && board.getPiece(endPos).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                if(endPos.getRow() == 1){
                    pawn.addAll(PawnPromotionLst(myPosition, endPos));
                }else {
                    pawn.add(myMove);
                }
            }
        }
        return pawn;
    }

    private ArrayList<ChessMove> PawnPromotionLst(ChessPosition start, ChessPosition endPos){
        ArrayList<ChessMove> myMoves = new ArrayList<>();
        for(PieceType Piece: PieceType.values()){
            if(Piece== PieceType.PAWN || Piece == PieceType.KING){
                continue;
            } else{
                ChessMove PromoMove = new ChessMove(start, endPos, Piece);
                myMoves.add(PromoMove);
            }
        }
        return myMoves;
    }

    public ChessPosition UpRight(int row, int col){
        ChessPosition end = new ChessPosition(row+1, col +1);
        return end;
    }

    public ChessPosition DownRight(int row, int col){
        ChessPosition end = new ChessPosition(row-1, col +1);
        return end;
    }

    public ChessPosition UpLeft(int row, int col){
        ChessPosition end = new ChessPosition(row+1, col -1);
        return end;
    }

    public ChessPosition DownLeft(int row, int col){
        ChessPosition end = new ChessPosition(row-1, col -1);
        return end;
    }

    public ChessPosition Up(int row, int col){
        ChessPosition end = new ChessPosition(row+1, col);
        return end;
    }

    public ChessPosition Down(int row, int col){
        ChessPosition end = new ChessPosition(row-1, col);
        return end;
    }

    public ChessPosition Right(int row, int col){
        ChessPosition end = new ChessPosition(row, col +1);
        return end;
    }

    public ChessPosition Left(int row, int col){
        ChessPosition end = new ChessPosition(row, col -1);
        return end;
    }

    public boolean sameTeam(ChessBoard board, ChessPosition position) {
        boolean result = false;
        if (board.getPiece(position).getTeamColor() == pColor){
            result = true;
        }
        return result;
    }

    public int colReset(ChessPosition position){
        int col = position.getColumn();
        return col;
    }

    public int rowReset(ChessPosition position){
        int row = position.getRow();
        return row;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pColor=" + pColor +
                ", pType=" + pType +
                '}';
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

