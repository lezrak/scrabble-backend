package com.lezrak.scrabblebackend.game;

import java.util.HashMap;

public class GetAiMoveResult {
    private HashMap<String, Character> move;
    private int score;

    public GetAiMoveResult(HashMap<String, Character> move, int score) {
        this.move = move;
        this.score = score;
    }

    public GetAiMoveResult() {
    }

    public HashMap<String, Character> getMove() {
        return move;
    }

    public void setMove(HashMap<String, Character> move) {
        this.move = move;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
