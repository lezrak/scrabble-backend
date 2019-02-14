package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.game.Game;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    private String email = "";
    private String nickname = "";
    private String password = "";
    private boolean isEnabled = false;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Game> games = new HashSet<>();


    public Player(String nickname) {
        this.nickname = nickname;
        this.isEnabled = true;
    }

    public Player() {
    }

    public Player addGame(Game game) {
        this.games.add(game);
        return this;
    }

    public boolean removeGame(Game game) {
        return this.games.remove(game);
    }

    public ArrayList<Game> getGames() {
        return new ArrayList<>(games);
    }


    public void enable() {
        isEnabled = true;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
