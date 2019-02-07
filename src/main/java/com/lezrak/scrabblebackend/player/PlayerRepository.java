package com.lezrak.scrabblebackend.player;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAll();

    Player findPlayerByNickname(String nickname);

    Player findPlayerByEmail(String email);

    Player findPlayerById(Long id);

}
