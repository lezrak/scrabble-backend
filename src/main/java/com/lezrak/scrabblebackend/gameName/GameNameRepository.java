package com.lezrak.scrabblebackend.gameName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameNameRepository extends JpaRepository <GameName, Long> {
    GameName findGameNameById(Long id);
}
