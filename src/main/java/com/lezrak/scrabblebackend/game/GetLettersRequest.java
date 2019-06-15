package com.lezrak.scrabblebackend.game;

import java.util.ArrayList;

public class GetLettersRequest {
    private ArrayList<Character> used;
    private int size;

    public GetLettersRequest(ArrayList<Character> used, int size) {
        this.used = used;
        this.size = size;
    }

    public GetLettersRequest() {
    }

    public GetLettersRequest(int size) {
        this.size = size;
        this.used = new ArrayList<>();
    }

    public ArrayList<Character> getUsed() {
        return used;
    }

    public void setUsed(ArrayList<Character> used) {
        this.used = used;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
