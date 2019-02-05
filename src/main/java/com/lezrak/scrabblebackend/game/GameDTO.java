package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.player.PlayerDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class GameDTO {
    private LinkedHashSet<PlayerStateDTO> players = new LinkedHashSet<>();

    private HashMap<String, Character> boardState = new HashMap<>();

    private int nextPlayer = -1;


    private String name;

    public GameDTO() {
    }

    public GameDTO(LinkedHashSet<PlayerStateDTO> players, HashMap<String, Character> boardState, int nextPlayer, String name) {
        this.players = players;
        this.boardState = boardState;
        this.nextPlayer = nextPlayer;
        this.name = name;
    }

    private class PlayerStateDTO {


        private PlayerDTO player;

        private int totalPoints = 0;

        private int lastMovePoints = 0;

        private ArrayList<Character> characters = new ArrayList<>();

        PlayerStateDTO(PlayerDTO player, int totalPoints, int lastMovePoints, ArrayList<Character> characters) {
            this.player = player;
            this.characters = characters;
            this.totalPoints = totalPoints;
            this.lastMovePoints = lastMovePoints;

        }
    }

    public LinkedHashSet<PlayerStateDTO> getPlayers() {
        return players;
    }

    public void setPlayers(LinkedHashSet<PlayerStateDTO> players) {
        this.players = players;
    }

    public HashMap<String, Character> getBoardState() {
        return boardState;
    }

    public void setBoardState(HashMap<String, Character> boardState) {
        this.boardState = boardState;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
