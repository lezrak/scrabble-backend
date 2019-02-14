package com.lezrak.scrabblebackend.game;


import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.player.Player;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PlayerState> players = new LinkedHashSet<>();

    private HashMap<String, Character> boardState = new HashMap<>();

    private int nextPlayer = -1;

    private boolean started = false;

    @Column(name = "gameName", unique = true, nullable = false, length = 60)
    private String name;

    public Game() {
    }

    public Game(String name) {
        this.name = name;
    }

    boolean startGame() {
        if (started) {
            return false;
        } else {
            nextPlayer = new Random().nextInt(players.size() + 1);
            return started = true;
        }
    }

    //TODO : Add getting move evaluation from AI server
    public boolean makeMove(Long playerId, HashMap<String, Character> move) {
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

    public Game removePlayer(Player player) {
        for (PlayerState p : players) {
            if (p.getPlayer().equals(player)) {
                players.remove(p);
                return this;
            }
        }
        return null;
    }

    public LinkedHashSet<PlayerState> getPlayers() {
        return new LinkedHashSet<>(players);
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


}
