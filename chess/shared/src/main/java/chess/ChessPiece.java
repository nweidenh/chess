package chess;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.HashSet;

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
        // throw new RuntimeException("Not implemented");
        return pColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        //throw new RuntimeException("Not implemented");
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
        //Bishops Move Collection
        if (board.getPiece(myPosition).getPieceType() == PieceType.BISHOP) {
            HashSet<ChessMove> bishops = new HashSet<ChessMove>();
            int column = myPosition.getColumn();
            for (int i = myPosition.getRow(); i < 8; i++) {
                // Up right
                if (column > 0 && column < 8) {
                    ChessPosition endPos;
                    endPos = UpRight(i, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        bishops.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        bishops.add(move);
                        break;
                    }
                    column++;
                }
            }
            column = colReset(myPosition);
            // Down Right
            for (int i = myPosition.getRow(); i > 1; i--) {
                if (column > 0 && column < 8) {
                    ChessPosition endPos;
                    endPos = DownRight(i, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        bishops.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        bishops.add(move);
                        break;
                    }
                    column++;
                }
            }
            column = colReset(myPosition);
            // Down Left
            for (int i = myPosition.getRow(); i > 1; i--) {
                if (column > 1 && column < 8) {
                    ChessPosition endPos;
                    endPos = DownLeft(i, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        bishops.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        bishops.add(move);
                        break;
                    }
                    column--;
                }
            }
            column = colReset(myPosition);
            //Up Left
            for (int i = myPosition.getRow(); i < 8; i++) {
                if (column > 1 && column < 8) {
                    ChessPosition endPos;
                    endPos = UpLeft(i, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        bishops.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        bishops.add(move);
                        break;
                    }
                    column--;
                }
            }
            return bishops;
        }

        //King Move Collection
        if (board.getPiece(myPosition).getPieceType() == PieceType.KING) {
            HashSet<ChessMove> kings = new HashSet<ChessMove>();
            int column = myPosition.getColumn();
            int row = myPosition.getRow();
            //Vertical + 1
            if (column > 0 && column < 9 && row > 0 && row < 8) {
                ChessPosition endPos = new ChessPosition(row + 1, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } //Diagnol Up Right
            if (column > 0 && column < 8 && row > 0 && row < 8) {
                ChessPosition endPos = new ChessPosition(row + 1, column + 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } // Right
            if (column > 0 && column < 8 && row > 0 && row < 9) {
                ChessPosition endPos = new ChessPosition(row, column + 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } // Diagnol Down Right
            if (column > 0 && column < 8 && row > 1 && row < 9) {
                ChessPosition endPos = new ChessPosition(row - 1, column + 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } // Down
            if (column > 0 && column < 9 && row > 1 && row < 9) {
                ChessPosition endPos = new ChessPosition(row - 1, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } // Diagnol Down Left
            if (column > 1 && column < 9 && row > 1 && row < 9) {
                ChessPosition endPos = new ChessPosition(row - 1, column - 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                } //Left
            }
            if (column > 1 && column < 9 && row > 0 && row < 9) {
                ChessPosition endPos = new ChessPosition(row, column - 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                } // Diagnol Left Up
            }
            if (column > 1 && column < 9 && row > 0 && row < 8) {
                ChessPosition endPos = new ChessPosition(row + 1, column - 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            }
            return kings;
        }
        //Knight Move Collection
        if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT) {
            HashSet<ChessMove> knights = new HashSet<ChessMove>();
            int column = myPosition.getColumn();
            int row = myPosition.getRow();
            //Up 2, Right 1
            if (column > 0 && column < 8 && row > 0 && row < 7) {
                ChessPosition endPos = new ChessPosition(row + 2, column + 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    knights.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    knights.add(move);
                }
            } //Right 2, Up 1
            if (column > 0 && column < 7 && row > 0 && row < 8) {
                ChessPosition endPos = new ChessPosition(row + 1, column + 2);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    knights.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    knights.add(move);
                }
            } // Right 2, Down 1
            if (column > 0 && column < 7 && row > 1 && row < 9) {
                ChessPosition endPos = new ChessPosition(row - 1, column + 2);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    knights.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    knights.add(move);
                }
            } // Down 2, Right 1
            if (column > 0 && column < 8 && row > 2 && row < 9) {
                ChessPosition endPos = new ChessPosition(row - 2, column + 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    knights.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    knights.add(move);
                }
            } // Down 2, Left 1
            if (column > 1 && column < 9 && row > 2 && row < 9) {
                ChessPosition endPos = new ChessPosition(row - 2, column - 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    knights.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    knights.add(move);
                }
            } // Left 2, Down 1
            if (column > 2 && column < 9 && row > 1 && row < 9) {
                ChessPosition endPos = new ChessPosition(row - 1, column - 2);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    knights.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    knights.add(move);
                } //Left 2, Up 1
            }
            if (column > 2 && column < 9 && row > 0 && row < 8) {
                ChessPosition endPos = new ChessPosition(row + 1, column - 2);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    knights.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    knights.add(move);
                } // Up 2, Left 1
            }
            if (column > 1 && column < 9 && row > 0 && row < 7) {
                ChessPosition endPos = new ChessPosition(row + 2, column - 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    knights.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    knights.add(move);
                }
            }
            return knights;
        }
        //Pawn Move Collection
        if(board.getPiece(myPosition).getPieceType() ==PieceType.PAWN) {
            HashSet<ChessMove> pawns = new HashSet<ChessMove>();
            int column = myPosition.getColumn();
            int row = myPosition.getRow();
            //Up 1
            if (getTeamColor() == ChessGame.TeamColor.WHITE && column > 0 && column < 9 && row > 0 && row < 8) {
                ChessPosition endPos;
                endPos = Up(row, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    if(endPos.getRow() == 8){
                        ArrayList<ChessMove> myMoves= new ArrayList<ChessMove>();
                        myMoves = pawnPromotionLst(myPosition, endPos);
                        pawns.addAll(myMoves);
                    } else {
                        pawns.add(move);
                    }
                } else{
                }
            } //Up 2
            if (getTeamColor() == ChessGame.TeamColor.WHITE && row == 2) {
                ChessPosition endPos = new ChessPosition(row + 2, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                ChessPosition moveCheck = new ChessPosition(endPos.getRow() -1, endPos.getColumn());
                if (board.getPiece(endPos) == null && board.getPiece(moveCheck) == null) {
                    pawns.add(move);
                } else{
                }
            } //Capture Up Right
            if (getTeamColor() == ChessGame.TeamColor.WHITE && column > 0 && column < 8 && row > 0 && row < 8) {
                ChessPosition endPos;
                endPos = UpRight(row, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                } else if (sameTeam(board, endPos)) {
                } else {
                    if(endPos.getRow() == 8){
                        ArrayList<ChessMove> myMoves= new ArrayList<ChessMove>();
                        myMoves = pawnPromotionLst(myPosition, endPos);
                        pawns.addAll(myMoves);
                    } else {
                        pawns.add(move);
                    }
                }
            } // Capture  Up Left
            if (getTeamColor() == ChessGame.TeamColor.WHITE && column > 0 && column < 9 && row > 0 && row < 8) {
                ChessPosition endPos;
                endPos = UpLeft(row, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                } else if (sameTeam(board, endPos)) {
                } else {
                    if(endPos.getRow() == 8){
                        ArrayList<ChessMove> myMoves= new ArrayList<ChessMove>();
                        myMoves = pawnPromotionLst(myPosition, endPos);
                        pawns.addAll(myMoves);
                    } else {
                        pawns.add(move);
                    }
                }
            } // Down 1
            if (getTeamColor() == ChessGame.TeamColor.BLACK && column > 0 && column < 9 && row > 1 && row < 9) {
                ChessPosition endPos;
                endPos = Down(row, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    if(endPos.getRow() == 1){
                        ArrayList<ChessMove> myMoves= new ArrayList<ChessMove>();
                        myMoves = pawnPromotionLst(myPosition, endPos);
                        pawns.addAll(myMoves);
                    } else {
                        pawns.add(move);
                    }
                } else{
                }
            } //Down 2
            if (getTeamColor() == ChessGame.TeamColor.BLACK && row == 7) {
                ChessPosition endPos = new ChessPosition(row - 2, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                ChessPosition moveCheck = new ChessPosition(endPos.getRow() +1, endPos.getColumn() );
                if (board.getPiece(endPos) == null && board.getPiece(moveCheck) == null) {
                    pawns.add(move);
                } else{
                }
            } //Capture Down Right
            if (getTeamColor() == ChessGame.TeamColor.BLACK && column > 0 && column < 8 && row > 1 && row < 9) {
                ChessPosition endPos;
                endPos = DownRight(row, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                } else if (sameTeam(board, endPos)) {
                } else {
                    if(endPos.getRow() == 1){
                        ArrayList<ChessMove> myMoves= new ArrayList<ChessMove>();
                        myMoves = pawnPromotionLst(myPosition, endPos);
                        pawns.addAll(myMoves);
                    } else {
                        pawns.add(move);
                    }
                }

            } // Capture  Down Left
            if (getTeamColor() == ChessGame.TeamColor.BLACK && column > 1 && column < 9 && row > 1 && row < 9) {
                ChessPosition endPos;
                endPos = DownLeft(row, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                } else if (sameTeam(board, endPos)) {
                } else {
                    if(endPos.getRow() == 1){
                        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();
                        myMoves = pawnPromotionLst(myPosition, endPos);
                        pawns.addAll(myMoves);
                    } else {
                        pawns.add(move);
                    }
                }
            }
            return pawns;
        }
        //Queens Move Collection
        if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN) {
            HashSet<ChessMove> Queens = new HashSet<ChessMove>();
            int column = myPosition.getColumn();
            int row = myPosition.getRow();
            for (int i = row; i < 8; i++) {
                // Up
                if (column > 0 && column < 9 && row > 0 && row < 8) {
                    ChessPosition endPos;
                    endPos = Up(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Queens.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Queens.add(move);
                        break;
                    } row++;
                }
            }column = colReset(myPosition);
            row = rowReset(myPosition);
            for (int i = row; i < 8; i++) {
                // Up right
                if (column > 0 && column < 8 && row > 0 && row < 8) {
                    ChessPosition endPos;
                    endPos = UpRight(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Queens.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Queens.add(move);
                        break;
                    }
                    row++;
                    column++;
                }
            }column = colReset(myPosition);
            row = rowReset(myPosition);
            //Right
            for (int i = column; i < 8; i++) {
                if (column > 0 && column < 8 && row > 0 && row < 9) {
                    ChessPosition endPos;
                    endPos = Right(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Queens.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Queens.add(move);
                        break;
                    }
                }column++;
            }
            column = colReset(myPosition);
            row = rowReset(myPosition);
            // Down Right
            for (int i = row; i > 1; i--) {
                if (column > 0 && column < 8 && row > 1 && row < 9) {
                    ChessPosition endPos;
                    endPos = DownRight(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Queens.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Queens.add(move);
                        break;
                    }
                    row--;
                    column++;
                }
            }column = colReset(myPosition);
            row = rowReset(myPosition);
            // Down
            for (int i = row; i > 1; i--) {
                if (column > 0 && column < 9 && row > 1 && row < 9) {
                    ChessPosition endPos;
                    endPos = Down(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Queens.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Queens.add(move);
                        break;
                    }
                }row--;
            }
            column = colReset(myPosition);
            row = rowReset(myPosition);
            // Down Left
            for (int i = row; i > 1; i--) {
                if (column > 1 && column < 9 && row > 1 && row < 9) {
                    ChessPosition endPos;
                    endPos = DownLeft(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Queens.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Queens.add(move);
                        break;
                    }
                    row--;
                    column--;
                }
            }
            column = colReset(myPosition);
            row = rowReset(myPosition);
            //Left
            for (int i = column; i > 1; i--) {
                if (column > 1 && column < 9 && row > 0 && row < 9) {
                    ChessPosition endPos;
                    endPos = Left(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Queens.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Queens.add(move);
                        break;
                    }
                    column--;
                }
            }column = colReset(myPosition);
            row = rowReset(myPosition);
            //Up Left
            for (int i = row; i < 8; i++) {
                if (column > 1 && column < 9 && row > 0 && row < 8) {
                    ChessPosition endPos;
                    endPos = UpLeft(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Queens.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Queens.add(move);
                        break;
                    }
                    row++;
                    column--;
                }
            }
            return Queens;
        }
        //Rook Move Collection
        if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK) {
            HashSet<ChessMove> Rook = new HashSet<ChessMove>();
            int column = myPosition.getColumn();
            int row = myPosition.getRow();
            for (int i = row; i < 8; i++) {
                // Up
                if (column > 0 && column < 9 && row > 0 && row < 8) {
                    ChessPosition endPos;
                    endPos = Up(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Rook.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Rook.add(move);
                        break;
                    } row++;
                }
            }column = colReset(myPosition);
            row = rowReset(myPosition);
            //Right
            for (int i = column; i < 8; i++) {
                if (column > 0 && column < 8 && row > 0 && row < 9) {
                    ChessPosition endPos;
                    endPos = Right(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Rook.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Rook.add(move);
                        break;
                    }
                }column++;
            }column = colReset(myPosition);
            row = rowReset(myPosition);
            // Down
            for (int i = row; i > 1; i--) {
                if (column > 0 && column < 9 && row > 1 && row < 9) {
                    ChessPosition endPos;
                    endPos = Down(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Rook.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Rook.add(move);
                        break;
                    }
                }row--;
            }
            column = colReset(myPosition);
            row = rowReset(myPosition);
            //Left
            for (int i = column; i > 1; i--) {
                if (column > 1 && column < 9 && row > 0 && row < 9) {
                    ChessPosition endPos;
                    endPos = Left(row, column);
                    ChessMove move = new ChessMove(myPosition, endPos, null);
                    if (board.getPiece(endPos) == null) {
                        Rook.add(move);
                    } else if (sameTeam(board, endPos)) {
                        break;
                    } else {
                        Rook.add(move);
                        break;
                    }
                    column--;
                }
            }
            return Rook;
        }
        return null;
    }

    private ArrayList<ChessMove> pawnPromotionLst(ChessPosition myPos, ChessPosition endPos){
        ArrayList<ChessMove> listOfMoves = new ArrayList<ChessMove>();
        for (PieceType piece: PieceType.values()){
            ChessMove myMove = new ChessMove(myPos, endPos, null);
            if (piece == PieceType.PAWN || piece == PieceType.KING){
                continue;
            }
            myMove.promotion = piece;
            listOfMoves.add(myMove);
        } return listOfMoves;
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
