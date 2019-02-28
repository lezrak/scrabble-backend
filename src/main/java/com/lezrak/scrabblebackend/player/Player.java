package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.exception.PlayerAlreadyEnabledException;
import com.lezrak.scrabblebackend.game.Game;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Entity
@Table(name = "players")
public class Player extends BaseEntity implements UserDetails {

    private String email = "";
    private String nickname = "";
    private String password = "";
    private boolean isEnabled = false;
    private HashSet<String> games = new HashSet<>();


    public Player(String nickname) {
        this.nickname = nickname;
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
        if(isEnabled){
            throw new PlayerAlreadyEnabledException(getNickname());
        }
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
