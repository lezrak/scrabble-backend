package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.player.Player;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;

@Entity
public class PlayerState extends BaseEntity {

    @ManyToOne
    private Player player;

    private int totalPoints = 0;

    private int lastMovePoints = 0;

    private ArrayList<Character> characters = new ArrayList<>();

    public PlayerState() {
    }

    PlayerState(Player player) {
        this.player = player;

    }

    public void addPoints(int points) {
        lastMovePoints = points;
        totalPoints += lastMovePoints;
    }
    public Player getPlayer() {
        return player;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getLastMovePoints() {
        return lastMovePoints;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }
}
