package com.lezrak.scrabblebackend.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAll();

    boolean existsPlayerByEmailAndNicknameAndPassword(String nickname, String email, String password);

    Player findPlayerById(Long id);

    boolean existsPlayerByNickname(String nickname);

    boolean existsPlayerByEmail(String email);

}
