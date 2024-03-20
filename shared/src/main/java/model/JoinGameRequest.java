package model;

public record JoinGameRequest(String playerColor, int gameID) {

    public JoinGameRequest makeUpperCase() {
        if(playerColor != null){
            return new JoinGameRequest(playerColor.toUpperCase(), gameID);
        } else return new JoinGameRequest(null, gameID);
    }
}
