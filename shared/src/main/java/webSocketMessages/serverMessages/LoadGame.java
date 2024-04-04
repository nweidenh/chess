package webSocketMessages.serverMessages;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;

public class LoadGame extends ServerMessage{

    protected ChessGame game;

    public LoadGame(ServerMessageType type, ChessGame game){
        super(type);
        this.serverMessageType = ServerMessageType.LOAD_GAME;
        this.game = game;
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    public ChessBoard getBoard() {
        return game.getBoard();
    }

    public ChessGame getGame(){
        return game;
    }
}
