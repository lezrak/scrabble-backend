package com.lezrak.scrabblebackend.player;

public class PlayerDTO {

    private String email;
    private String nickname;
    private String password;
    private Long id;

    public PlayerDTO(String email, String nickname, Long id) {
        this.email = email;
        this.nickname = nickname;
        this.id = id;
    }

    public PlayerDTO(String nickname, Long id) {
        this.nickname = nickname;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
