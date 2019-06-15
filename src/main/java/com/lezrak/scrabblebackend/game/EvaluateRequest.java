package com.lezrak.scrabblebackend.game;

import java.util.HashMap;

public class EvaluateRequest {
    private HashMap<String, Character> move;
    private HashMap<String, Character> board;

    public EvaluateRequest() {
    }

    public EvaluateRequest(HashMap<String, Character> move, HashMap<String, Character> board) {
        this.move = move;
        this.board = board;
    }

    public HashMap<String, Character> getMove() {
        return move;
    }

    public void setMove(HashMap<String, Character> move) {
        this.move = move;
    }

    public HashMap<String, Character> getBoard() {
        return board;
    }

    public void setBoard(HashMap<String, Character> board) {
        this.board = board;
    }
}
