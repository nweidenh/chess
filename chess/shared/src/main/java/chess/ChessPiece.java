package chess;

import java.security.KeyStore;
import java.util.ArrayList;
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
            ArrayList<ChessMove> bishops = new ArrayList<ChessMove>();
            int column = myPosition.getColumn();
            for (int i = myPosition.getRow(); i < 8; i++) {
                if (column > 1 && column < 8) {
                    ChessPosition endPos = new ChessPosition(i + 1, column + 1);
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
            for (int i = myPosition.getRow(); i > 1; i--) {
                if (column > 1 && column < 8) {
                    ChessPosition endPos = new ChessPosition(i - 1, column + 1);
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
            for (int i = myPosition.getRow(); i > 1; i--) {
                if (column > 1 && column < 8) {
                    ChessPosition endPos = new ChessPosition(i - 1, column - 1);
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
            for (int i = myPosition.getRow(); i < 8; i++) {
                if (column > 1 && column < 8) {
                    ChessPosition endPos = new ChessPosition(i + 1, column - 1);
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
            ArrayList<ChessMove> kings = new ArrayList<ChessMove>();
            int column = myPosition.getColumn();
            int row = myPosition.getRow();
            //Vertical + 1
            if (column > 1 && column < 8 && row > 1 && row < 8) {
                ChessPosition endPos = new ChessPosition(row + 1, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } //Diagnol Up Right
            if (column > 1 && column < 8 && row > 1 && row < 8) {
                ChessPosition endPos = new ChessPosition(row + 1, column + 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } // Right
            if (column > 1 && column < 8 && row > 1 && row < 8) {
                ChessPosition endPos = new ChessPosition(row, column + 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } // Diagnol Down Right
            if (column > 1 && column < 8 && row > 1 && row < 8) {
                ChessPosition endPos = new ChessPosition(row - 1, column + 1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } // Down
            if (column > 1 && column < 8 && row > 1 && row < 8) {
                ChessPosition endPos = new ChessPosition(row - 1, column);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                }
            } // Diagnol Down Left
            if (column > 1 && column < 8 && row > 1 && row < 8) {
                ChessPosition endPos = new ChessPosition(row -1, column -1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                } //Left
            }if (column > 1 && column < 8 && row > 1 && row < 8) {
                ChessPosition endPos = new ChessPosition(row , column-1);
                ChessMove move = new ChessMove(myPosition, endPos, null);
                if (board.getPiece(endPos) == null) {
                    kings.add(move);
                } else if (sameTeam(board, endPos)) {
                } else {
                    kings.add(move);
                } // Diagnol Left Up
            }if (column > 1 && column < 8 && row > 1 && row < 8) {
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
        return null;
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
