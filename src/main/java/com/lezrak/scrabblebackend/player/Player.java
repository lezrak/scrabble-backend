package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    private String email;
    private String nickname;
    private String password;
    private boolean isEnabled = false;

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
