package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.player.PlayerDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class GameDTO {

    private LinkedHashSet<PlayerStateDTO> players = new LinkedHashSet<>();

    private HashMap<String, Character> boardState = new HashMap<>();

    private int nextPlayer = -1;

    private String nextPlayerName = "";

    private String name;

    public GameDTO() {
    }

    GameDTO(LinkedHashSet<PlayerStateDTO> players, HashMap<String, Character> boardState, int nextPlayer, String name, String nextPlayerName) {
        this.players = players;
        this.boardState = boardState;
        this.nextPlayer = nextPlayer;
        this.name = name;
        this.nextPlayerName = nextPlayerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDTO that = (GameDTO) o;
        return this.name.equals(that.getName())
                && this.nextPlayer == that.getNextPlayer()
                && this.boardState.equals(that.getBoardState())
                && this.players.containsAll(that.getPlayers());
    }

    public LinkedHashSet<PlayerStateDTO> getPlayers() {
        return players;
    }


    public HashMap<String, Character> getBoardState() {
        return boardState;
    }


    public int getNextPlayer() {
        return nextPlayer;
    }


    public String getName() {
        return name;
    }

    public String getNextPlayerName() {
        return nextPlayerName;
    }

    public static class PlayerStateDTO {


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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameDTO.PlayerStateDTO that = (GameDTO.PlayerStateDTO) o;
            return this.player.equals(that.player)
                    && this.characters.equals(that.characters)
                    && this.totalPoints == (that.totalPoints)
                    && this.lastMovePoints == (that.lastMovePoints);
        }

        public int getLastMovePoints() {
            return lastMovePoints;
        }

        public PlayerDTO getPlayer() {
            return player;
        }

        public int getTotalPoints() {
            return totalPoints;
        }

        public ArrayList<Character> getCharacters() {
            return characters;
        }

        @Override
        public int hashCode() {
            return (int) totalPoints * lastMovePoints * characters.hashCode();
        }


    }


}
