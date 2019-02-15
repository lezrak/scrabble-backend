package com.lezrak.scrabblebackend.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAll();

    Game findByName(String name);

    List<Game> findByStartedFalse();

}
