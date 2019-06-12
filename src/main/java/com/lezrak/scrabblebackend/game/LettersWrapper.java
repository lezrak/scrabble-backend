package com.lezrak.scrabblebackend.game;

import java.util.ArrayList;
import java.util.Arrays;

public class LettersWrapper {
    private String[] result;

    public String[] getResult() {
        return result;
    }

    public ArrayList<Character> getCharResult(){
        ArrayList<Character> characters = new ArrayList<>();
        Arrays.stream(result).forEach(e-> characters.add(e.charAt(0)));
        return characters;
    }

    public void setResult(String[] result) {
        this.result = result;
    }
}
