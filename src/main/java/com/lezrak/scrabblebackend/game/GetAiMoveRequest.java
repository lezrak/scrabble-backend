package com.lezrak.scrabblebackend.game;

import java.util.ArrayList;
import java.util.HashMap;

public class GetAiMoveRequest {
    private ArrayList<Character> hand;
    private HashMap<String, Character> board;

    public GetAiMoveRequest(ArrayList<Character> hand, HashMap<String, Character> board) {
        this.hand = hand;
        this.board = board;
    }

    public GetAiMoveRequest() {
    }

    public ArrayList<Character> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Character> hand) {
        this.hand = hand;
    }

    public HashMap<String, Character> getBoard() {
        return board;
    }

    public void setBoard(HashMap<String, Character> board) {
        this.board = board;
    }
}
