package com.lezrak.scrabblebackend.game;


import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.exception.*;
import com.lezrak.scrabblebackend.player.Player;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.persistence.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {


    //todo:sorting players
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

    //todo: substitute with REST Template
    public void makeMove(Long playerId, HashMap<String, Character> move) {

        if (!players.get(nextPlayer).getPlayer().getId().equals(playerId)) {
            throw new NotYourTurnException();
        }
        URIBuilder builder = null;
        int points = 0;
        try {
            builder = new URIBuilder("https://scrabble-ai-mock.herokuapp.com/move");
            builder.setParameter("boardState", this.boardState.toString()).setParameter("move", move.toString());
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(builder.build());
            CloseableHttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            points = Integer.valueOf(EntityUtils.toString(entity, "UTF-8"));

        } catch (IOException | URISyntaxException e) {
            throw new ApplicationMaintenanceException();
        }

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
