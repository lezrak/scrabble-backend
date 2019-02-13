package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.common.NicknameInUseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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


    @Override
    public PlayerDTO addPlayer(PlayerDTO playerDTO) {
        if (nicknameIsUsed(playerDTO.getNickname())) {
            throw new NicknameInUseException(playerDTO.getNickname());
        }
        Player player = new Player(playerDTO.getNickname());
        if (playerDTO.getEmail().length() > 0) {
            if (emailIsUsed(playerDTO.getEmail())) {
                throw new EmailInUseException(playerDTO.getEmail());
            }
            player.setEmail(playerDTO.getEmail());
        }
        if (playerDTO.getPassword().length() > 0)
            player.setPassword(playerDTO.getPassword());
        return PlayerMapper.toPlayerDTO(playerRepository.save(player));
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
