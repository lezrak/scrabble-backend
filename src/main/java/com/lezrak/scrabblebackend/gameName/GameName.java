package com.lezrak.scrabblebackend.gameName;

import com.lezrak.scrabblebackend.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gameNames")
public class GameName extends BaseEntity {
    private String name;

    public GameName(String name) {
        this.name = name;
    }

    public GameName() {
    }

    public String getName() {
        return name;
    }
}
