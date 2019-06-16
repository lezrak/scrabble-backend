package com.lezrak.scrabblebackend.game;


import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.exception.GameAlreadyStartedException;
import com.lezrak.scrabblebackend.exception.GameFullException;
import com.lezrak.scrabblebackend.exception.NotYourTurnException;
import com.lezrak.scrabblebackend.exception.PlayerNotInGameException;
import com.lezrak.scrabblebackend.player.Player;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings({"WeakerAccess", "Duplicates"})
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

    @Transactional
    public void startGame() {
        if (started) {
            throw new GameAlreadyStartedException(name);
        } else {
            started = true;
            getLettersForStart();
            nextPlayer = new Random().nextInt(players.size());
            checkForAIMove();
        }
    }

    @Transactional
    public void getLettersForStart() {
        String transactionUrl = "https://fierce-retreat-89489.herokuapp.com/util/drawTiles";

//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromUriString(transactionUrl)
//                .queryParam("used", "")
//                .queryParam("size", players.size() * 7);

        RestTemplate restTemplate = new RestTemplate();
//        LettersWrapper letters = restTemplate.getForObject(builder.toUriString(), LettersWrapper.class);
        LettersWrapper letters = restTemplate.postForObject(transactionUrl, new GetLettersRequest(players.size() * 7), LettersWrapper.class);
        System.out.println("Letters received from AI server:");
        System.out.println(letters.getCharResult());

        int a = 0;
        for (PlayerState p : players) {
            p.addCharacters(new ArrayList<>(letters.getCharResult().subList(a, a + 7)));
            a += 7;
        }
    }

    @Transactional
    public void getLetters(int i) {
        ArrayList<Character> helper = new ArrayList<>(boardState.values());
        for (PlayerState playerState : players) {
            helper.addAll(playerState.getCharacters());
        }

//        StringBuilder used = new StringBuilder();
//        for (Character c : helper) {
//            used.append(c);
//        }

        String transactionUrl = "https://fierce-retreat-89489.herokuapp.com/util/drawTiles";

//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromUriString(transactionUrl)
//                .queryParam("used",used.toString())
//                .queryParam("size", i);

        RestTemplate restTemplate = new RestTemplate();
        LettersWrapper lettersWrapper = restTemplate.postForObject(transactionUrl, new GetLettersRequest(helper, i), LettersWrapper.class);
//        LettersWrapper lettersWrapper = restTemplate.getForObject(builder.toUriString(), LettersWrapper.class);
        players.get(nextPlayer).addCharacters(lettersWrapper.getCharResult());
        System.out.println("Letters received from AI server:");
        System.out.println(lettersWrapper.getCharResult());

    }

    public void makeMove(Long playerId, HashMap<String, Character> move) {

        players.sort(PlayerState::compareTo);
        if (!players.get(nextPlayer).getPlayer().getId().equals(playerId)) {
            throw new NotYourTurnException();
        }

        String transactionUrl = "https://fierce-retreat-89489.herokuapp.com/eval/evaluate";

//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromUriString(transactionUrl)
//                .queryParam("board", this.boardState.toString())
//                .queryParam("move", move.toString());

        RestTemplate restTemplate = new RestTemplate();
        PointsWrapper pointsWrapper = restTemplate.postForObject(transactionUrl, new EvaluateRequest(move, boardState), PointsWrapper.class);
//        PointsWrapper pointsWrapper = restTemplate.getForObject(builder.toUriString(), PointsWrapper.class);
        String pointsAsString = pointsWrapper.pointsAsString();
        int points = 0;
        if (pointsAsString != null) {
            points = Integer.parseInt(pointsAsString.replaceAll("[^0-9.]", ""));
        }
        if (points < 0) {
            throw new RuntimeException("AI eval error");
        }
        if (points > 0) {
            move.values().forEach(players.get(nextPlayer)::removeCharacter);
            boardState.putAll(move);
            getLetters(move.size());
        }
        for (PlayerState p : players) {
            if (p.getPlayer().getId().equals(playerId)) {
                p.addPoints(points);
            }
        }
        nextPlayer = (nextPlayer + 1) % players.size();
        checkForAIMove();
    }

    private void checkForAIMove() {
        if (players.get(nextPlayer).getPlayer().isAI()) {
            makeAIMove();
        }
    }

    private void makeAIMove() {
        //todo get AI move prediction from AI server

        //todo get AI move eval from AI serer

        //todo copy logic from makeMove to adjust boardState and AI points

        checkForAIMove();
    }

    public String getHint() {
        ArrayList<Character> characters = players.get(nextPlayer).getCharacters();

        //todo get hint form AI server using characters and gameState


        return "uzyskana podpowiedź";
    }

    public void tradeLetters(Long playerId, ArrayList<Character> characters) {
        players.sort(PlayerState::compareTo);
        if (!players.get(nextPlayer).getPlayer().getId().equals(playerId)) {
            throw new NotYourTurnException();
        }
        characters.forEach(players.get(nextPlayer)::removeCharacter);
        getLetters(characters.size());
        for (PlayerState p : players) {
            if (p.getPlayer().getId().equals(playerId)) {
                p.addPoints(0);
            }
        }
        nextPlayer = (nextPlayer + 1) % players.size();
        checkForAIMove();
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
        players.sort(PlayerState::compareTo);
        return new LinkedHashSet<>(players);
    }

    public HashMap<String, Character> getBoardState() {
        return boardState;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public String getNextPlayerName() {
        if (nextPlayer < 0) {
            return "Game not started yet.";
        } else {
            return players.get(nextPlayer).getPlayer().getNickname();
        }
    }

    public String getName() {
        return name;
    }

    public boolean isStarted() {
        return started;
    }
}
