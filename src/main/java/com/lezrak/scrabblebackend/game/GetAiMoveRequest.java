package com.lezrak.scrabblebackend.game;

import java.util.ArrayList;
import java.util.HashMap;

public class GetAiMoveRequest {
    private ArrayList<Character> letters;
    private HashMap<String, Character> board;

    public GetAiMoveRequest(ArrayList<Character> letters, HashMap<String, Character> board) {
        this.letters = letters;
        this.board = board;
    }

    public GetAiMoveRequest() {
    }

    public ArrayList<Character> getLetters() {
        return letters;
    }

    public void setLetters(ArrayList<Character> letters) {
        this.letters = letters;
    }

    public HashMap<String, Character> getBoard() {
        return board;
    }

    public void setBoard(HashMap<String, Character> board) {
        this.board = board;
    }
}
