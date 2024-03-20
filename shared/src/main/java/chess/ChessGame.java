package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    public TeamColor teamTurn;
    public ChessBoard board;

    public ChessGame() {
        this.board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        HashSet<ChessMove> possibleMoves = new HashSet<>(this.board.getPiece(startPosition).pieceMoves(this.board, startPosition));
        HashSet<ChessMove> verifiedMoves = new HashSet<>();
        for(ChessMove move : possibleMoves) {
            //Makes 2 new boards
            ChessBoard boardCopy = new ChessBoard(board);
            ChessBoard originalBoard = new ChessBoard(board);
            //Makes the proposed move on the copy of the board
            boardCopy.addPiece(move.getEndPosition(),boardCopy.getPiece(startPosition));
            boardCopy.removePiece(startPosition);
            //Set to copy of board
            setBoard(boardCopy);
            if(!isInCheck(boardCopy.getPiece(move.getEndPosition()).getTeamColor())){
                verifiedMoves.add(move);
            }
            setBoard(originalBoard);
        }
        return verifiedMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition proposedMoveEnd = move.getEndPosition();
        ChessPosition proposedMoveStart = move.getStartPosition();
        ChessPiece proposedPromotion = null;
        if (move.getPromotionPiece() != null) {
            proposedPromotion = new ChessPiece(board.getPiece(proposedMoveStart).getTeamColor(), move.getPromotionPiece());
        }
        ChessPiece pieceMoving = board.getPiece(move.getStartPosition());
        Collection<ChessMove> moveCollection = validMoves(proposedMoveStart);
        if(!moveCollection.contains(move)){
            throw new InvalidMoveException("Off the board move or leaves team king in Danger");
        } else if(pieceMoving.getTeamColor() != this.getTeamTurn()){
            throw new InvalidMoveException("Wrong team attempting to move");
        } else{
            if (proposedPromotion != null){
                this.board.addPiece(proposedMoveEnd, proposedPromotion);
            } else{
                this.board.addPiece(proposedMoveEnd, pieceMoving);
            }
            this.board.removePiece(proposedMoveStart);
            this.changeTeamTurn();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean isCheck = false;
        ChessPosition kingSpot = getYourKingPosition(this.getBoard(), teamColor);
        for (int i = 1; i < 9; i++){
            if(isCheck){
                break;
            }
            for (int  j = 1; j < 9; j++){
                ChessPosition currentSpace = new ChessPosition(i, j);
                if(this.getBoard().getPiece(currentSpace) != null &&
                   this.getBoard().getPiece(currentSpace).getTeamColor() != teamColor){
                    Collection<ChessMove> potentialMoves = new HashSet<>(
                            this.getBoard().getPiece(currentSpace).pieceMoves(this.getBoard(), currentSpace));
                    for(ChessMove moveTest : potentialMoves){
                        if (moveTest.getEndPosition().equals(kingSpot)){
                            isCheck = true;
                            break;
                        }
                    }
                }
            }
        }
        return isCheck;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && isInStalemate(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean isStalemate = true;
        for (int i = 1; i < 9; i++){
            if(!isStalemate){
                break;
            }
            for (int  j = 1; j < 9; j++){
                ChessPosition currentSpace = new ChessPosition(i, j);
                if(this.getBoard().getPiece(currentSpace) != null &&
                   this.getBoard().getPiece(currentSpace).getTeamColor() == teamColor){
                    Collection<ChessMove> potentialMoves = new HashSet<>(
                            validMoves(currentSpace));
                    if(!potentialMoves.isEmpty()){
                        isStalemate = false;
                        break;
                    }
                }
            }
        } return isStalemate;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }

    private ChessPosition getYourKingPosition(ChessBoard board, TeamColor team){
        ChessPosition king;
        for (int i = 1; i < 9; i++){
            for (int  j = 1; j < 9; j++) {
                if(board.getPiece(new ChessPosition(i, j)) != null &&
                   board.getPiece(new ChessPosition(i, j)).getTeamColor() == team &&
                   board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING){
                    king = new ChessPosition(i,j);
                    return king;
                }
            }
        }
        return null;
    }

    private void changeTeamTurn(){
        if(teamTurn == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        } else{
            setTeamTurn(TeamColor.WHITE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessGame chessGame)) return false;
        return getTeamTurn() == chessGame.getTeamTurn() && Objects.equals(getBoard(), chessGame.getBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeamTurn(), getBoard());
    }


}
