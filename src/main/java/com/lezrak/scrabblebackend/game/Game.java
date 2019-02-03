package com.lezrak.scrabblebackend.game;


import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.player.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    @ManyToMany(mappedBy = "games")
    private Set<Player> players = new HashSet<>();

    private ArrayList<Integer> totalPoints = new ArrayList<>();

    private ArrayList<Integer> lastMovePoints = new ArrayList<>();

    private ArrayList<ArrayList<Character>> characters = new ArrayList<>();

    private HashMap<String, Character> boardState = new HashMap<>();

    private int nextPlayer = -1;

    public boolean startGame() {
        if (nextPlayer < 0) {
            return false;
        } else {
            nextPlayer = new Random().nextInt(players.size() + 1);
            return true;
        }
    }

    public boolean makeMove(int id, HashMap<String,Character> move){
        //TODO : Add getting move evaluation from AI server
        nextPlayer = (nextPlayer+1)%players.size();
        return true;
    }


}
