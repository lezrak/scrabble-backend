package com.lezrak.scrabblebackend.player;

import org.springframework.beans.factory.annotation.Autowired;

public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public boolean checkCredentials(String nickname, String password) {
        if (!nicknameIsUsed(nickname)) {
            return false;
        }
        return playerRepository.findPlayerByNickname(nickname).getPassword().equals(password);
    }

    //TODO : does empty email come as null or emptyString? Adjust method
    @Override
    public PlayerDTO addPlayer(PlayerDTO playerDTO) {

        if (playerDTO.getEmail() != null) {
            if (!nicknameIsUsed(playerDTO.getNickname()) && !emailIsUsed(playerDTO.getEmail())) {
                return PlayerMapper.toPlayerDTO(playerRepository.save(
                        new Player(playerDTO.getEmail(), playerDTO.getNickname(), playerDTO.getPassword())));
            }
        } else if (!nicknameIsUsed(playerDTO.getNickname())) {
            return PlayerMapper.toPlayerDTO(playerRepository.save(
                    new Player(playerDTO.getNickname(), playerDTO.getPassword())));
        }


        return null;
    }


    @Override
    public boolean nicknameIsUsed(String nickname) {
        return playerRepository.existsPlayerByNickname(nickname);
    }

    @Override
    public boolean emailIsUsed(String email) {
        return playerRepository.existsPlayerByEmail(email);
    }
}
