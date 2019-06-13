package com.lezrak.scrabblebackend.game;

import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.player.Player;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.ArrayList;

@Entity
public class PlayerState extends BaseEntity implements Comparable<PlayerState>{

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Player player;

    private int totalPoints = 0;

    private int lastMovePoints = 0;

    private ArrayList<Character> characters = new ArrayList<>();

    public PlayerState() {
    }

    PlayerState(Player player) {
        this.player = player;
    }

    @Override
    public int compareTo(PlayerState o) {
        return player.getNickname().compareTo(o.getPlayer().getNickname());
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

    @Transactional
    public void addCharacters(ArrayList<Character> newChars) {
        characters.addAll(newChars);
    }

    public void removeCharacters(ArrayList<Character> usedChars) {
        usedChars.forEach(this::removeCharacter);
    }

    public void removeCharacter(Character usedChar) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).equals(usedChar)) {
                characters.remove(i);
                break;
            }
        }
    }
}
