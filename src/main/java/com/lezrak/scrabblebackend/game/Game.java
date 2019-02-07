package com.lezrak.scrabblebackend.game;


import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.player.Player;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {


    private LinkedHashSet<PlayerState> players = new LinkedHashSet<>();

    private HashMap<String, Character> boardState = new HashMap<>();

    private int nextPlayer = -1;

    private boolean started = false;

    @Column(name = "gameName", unique = true, nullable = false, length = 60)
    private String name;

    public Game() {
    }
    public Game(String name){
        this.name = name;
    }

    public boolean startGame() {
        if (started) {
            return false;
        } else {
            nextPlayer = new Random().nextInt(players.size() + 1);
            return started = true;
        }
    }

    public boolean makeMove(Long playerId, HashMap<String, Character> move) {
        //TODO : Add getting move evaluation from AI server
        nextPlayer = (nextPlayer + 1) % players.size();
        return true;
    }

    public boolean addPlayer(Player player) {
        if (players.size() < 4) {
            players.add(new PlayerState(player));
            return true;
        } else {
            return false;
        }
    }

    public boolean removePlayer(Player player) {
        for (PlayerState p : players) {
            if (p.player.equals(player)) {
                players.remove(p);
                return true;
            }
        }
        return false;
    }


    public LinkedHashSet<PlayerState> getPlayers() {
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

    public static class PlayerState {


        @ManyToOne
        private Player player;

        private int totalPoints = 0;

        private int lastMovePoints = 0;

        private ArrayList<Character> characters = new ArrayList<>();

        PlayerState(Player player) {
            this.player = player;

        }

        public Player getPlayer() {
            return player;
        }

        public int getTotalPoints() {
            return totalPoints;
        }

        public int getLastMovePoints() {
            return lastMovePoints;
        }

        public ArrayList<Character> getCharacters() {
            return characters;
        }
    }


}
