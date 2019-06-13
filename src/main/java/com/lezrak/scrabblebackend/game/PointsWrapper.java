package com.lezrak.scrabblebackend.game;

public class PointsWrapper {
    private int result;

    public int getResult() {
        return result;
    }

    public String pointsAsString(){
        return String.valueOf(result);
    }

    public void setResult(int result) {
        this.result = result;
    }
}
