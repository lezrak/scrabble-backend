package com.lezrak.scrabblebackend.gameName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gameNames")
public class GameName {
    private static int counter = 0;

    @Id
    private int id;

    @Column(unique = true, nullable = false, updatable = false, length = 55)
    private String name;

    public GameName() {
    }

    public GameName(String name) {
        this.id = counter;
        this.name = name;
        counter++;
    }



    public String getName() {
        return name;
    }
}
