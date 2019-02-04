package com.lezrak.scrabblebackend.game;


import com.lezrak.scrabblebackend.common.BaseEntity;
import com.lezrak.scrabblebackend.player.Player;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {


    private LinkedHashSet<PlayerState> players = new LinkedHashSet<>();

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

    public boolean makeMove(long playerId, HashMap<String,Character> move){
        //TODO : Add getting move evaluation from AI server
        nextPlayer = (nextPlayer+1)%players.size();
        return true;
    }

    public boolean addPlayer(Player player){
        if(players.size()<4){
            players.add(new PlayerState(player));
            return true;
        }
        else {
            return false;
        }
    }

    public boolean removePlayer(Player player){
        for(PlayerState p : players){
            if(p.player.equals(player)){
                players.remove(p);
                return true;
            }
        }
        return false;
    }



    private class PlayerState{


        @ManyToOne
        private Player player;

        private int totalPoints=0;

        private int lastMovePoints=0;

        private ArrayList<Character> characters = new ArrayList<>();

        PlayerState(Player player){
            this.player = player;

        }
    }


}
