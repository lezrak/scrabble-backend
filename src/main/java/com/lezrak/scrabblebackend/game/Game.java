package com.lezrak.scrabblebackend.game;


import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.exception.GameAlreadyStartedException;
import com.lezrak.scrabblebackend.exception.GameFullException;
import com.lezrak.scrabblebackend.exception.NotYourTurnException;
import com.lezrak.scrabblebackend.exception.PlayerNotInGameException;
import com.lezrak.scrabblebackend.player.Player;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PlayerState> players = new ArrayList<>();

    private HashMap<String, Character> boardState = new HashMap<>();

    private int nextPlayer = -1;

    private boolean started = false;

    @Column(name = "game_name", unique = true, nullable = false, length = 60)
    private String name;

    public Game() {
    }

    public Game(String name) {
        this.name = name;
    }

    public void startGame() {
        if (started) {
            throw new GameAlreadyStartedException(name);
        } else {
            started = true;
            nextPlayer = new Random().nextInt(players.size());
        }
    }

    public void makeMove(Long playerId, HashMap<String, Character> move) {

        players.sort(PlayerState::compareTo);
        if (!players.get(nextPlayer).getPlayer().getId().equals(playerId)) {
            throw new NotYourTurnException();
        }


        String transactionUrl = "https://scrabble-ai-mock.herokuapp.com/move";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(transactionUrl)
                .queryParam("boardState", this.boardState.toString())
                .queryParam("move", move.toString());

        RestTemplate restTemplate = new RestTemplate();
        Integer points = restTemplate.getForObject(builder.toUriString(), Integer.class);


        for (PlayerState p : players) {
            if (p.getPlayer().getId().equals(playerId)) {
                p.addPoints(points);
            }
        }
        nextPlayer = (nextPlayer + 1) % players.size();
    }


    public void addPlayer(Player player) {
        if (players.size() < 4) {
            players.add(new PlayerState(player));
        } else {
            throw new GameFullException(this.name);
        }
    }

    public Game removePlayer(Player player) {
        for (PlayerState p : players) {
            if (p.getPlayer().equals(player)) {
                players.remove(p);
                return this;
            }
        }
        throw new PlayerNotInGameException(player.getNickname(), this.getName());
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
