package com.lezrak.scrabblebackend.player;

public class PlayerDTO {

    private String email;
    private String nickname;
    private String password;

    public PlayerDTO(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public PlayerDTO(String nickname) {
        this.nickname = nickname;
    }

    public PlayerDTO() {
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
