package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.game.Game;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    private String email = "";
    private String nickname = "";
    private String password = "";
    private boolean isEnabled = false;
    private HashSet<String> games = new HashSet<>();


    public Player(String nickname) {
        this.nickname = nickname;
        this.isEnabled = true;
    }

    public Player() {
    }

    public Player addGame(Game game) {
        this.games.add(game.getName());
        return this;
    }

    public Player removeGame(Game game) {
        this.games.remove(game.getName());
        return this;
    }

    public ArrayList<String> getGames() {
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
