package com.lezrak.scrabblebackend.player;

public class PlayerDTO {

    private String email;
    private String nickname;
    private String password;
    private Long id;

    public PlayerDTO() {
        this("", "", "", null);
    }


    public PlayerDTO(String email, String nickname, Long id) {
        this.nickname = nickname;
        this.email = email;
        this.id = id;
    }

    public PlayerDTO(String email, String nickname, String password, Long id) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
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

    public Long getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDTO that = (PlayerDTO) o;
        return this.email.equals(that.email)
                && this.nickname.equals(that.nickname)
                && this.password.equals(that.password)
                && this.id.equals(that.id);
    }

}
