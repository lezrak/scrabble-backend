package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.game.Game;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player extends BaseEntity implements Serializable {

    private String email;
    private String nickname;
    private String password;
    private boolean isEnabled = false;

    @ManyToMany
    private Set<Game> games = new HashSet<>();

    public Player(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Player(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.isEnabled = true;
    }

    public Player(String nickname) {
        this.nickname = nickname;
        this.isEnabled = true;
    }

    public Player() {
    }

    public boolean addGame(Game game){
        return this.games.add(game);
    }
    public boolean removeGame(Game game){
        return this.games.remove(game);
    }

    public ArrayList<Game> getGames(){
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
