package model;

import chess.ChessGame;

public record createGameRequest(String whiteUsername, String blackUsername, String gameName, ChessGame game) { }
