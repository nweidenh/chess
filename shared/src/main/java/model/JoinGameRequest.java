package model;

import chess.ChessGame;

import java.util.Objects;

public record JoinGameRequest(String playerColor, int gameID) {

    public JoinGameRequest makeUpperCase() {
        if(playerColor != null){
            return new JoinGameRequest(playerColor.toUpperCase(), gameID);
        } else return new JoinGameRequest(null, gameID);
    }

    public ChessGame.TeamColor getTeamColor(){
        if (playerColor != null) {
            if (Objects.equals(playerColor.toLowerCase(), "black")) {
                return ChessGame.TeamColor.BLACK;
            } else {
                return ChessGame.TeamColor.WHITE;
            }
        } return null;
    }
}
