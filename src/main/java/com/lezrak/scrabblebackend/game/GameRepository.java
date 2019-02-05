package com.lezrak.scrabblebackend.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAll();

    List<Game> findAllByNextPlayer(int nextPlayer);
}
