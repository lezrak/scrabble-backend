package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.exceptionHandling.EmailInUseException;
import com.lezrak.scrabblebackend.exceptionHandling.NicknameInUseException;
import com.lezrak.scrabblebackend.exceptionHandling.PlayerNotFoundException;
import com.lezrak.scrabblebackend.game.GameDTO;
import com.lezrak.scrabblebackend.game.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public boolean checkCredentials(PlayerDTO playerDTO) {
        return playerRepository.existsPlayerByEmailAndNicknameAndPassword(
                playerDTO.getEmail(), playerDTO.getNickname(), playerDTO.getPassword());
    }


    @Override
    public PlayerDTO postPlayer(PlayerDTO playerDTO) {
        if (nicknameIsUsed(playerDTO.getNickname())) {throw new NicknameInUseException(playerDTO.getNickname());}
        Player player = new Player(playerDTO.getNickname());
        if (playerDTO.getEmail().length() > 0) {
            if (emailIsUsed(playerDTO.getEmail())) {throw new EmailInUseException(playerDTO.getEmail());}
            player.setEmail(playerDTO.getEmail());
        }
        if (playerDTO.getPassword().length() > 0)player.setPassword(playerDTO.getPassword());
        return PlayerMapper.toPlayerDTO(playerRepository.save(player));
    }

    @Override
    public List<GameDTO> getGames(Long id) {
        if(!playerRepository.existsPlayerById(id)) throw new PlayerNotFoundException(id.toString());
        return GameMapper.toGameDTO(playerRepository.findPlayerById(id).getGames());
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
