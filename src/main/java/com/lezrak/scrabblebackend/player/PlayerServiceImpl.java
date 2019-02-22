package com.lezrak.scrabblebackend.player;

import com.lezrak.scrabblebackend.emailVerification.VerificationTokenService;
import com.lezrak.scrabblebackend.exceptionHandling.EmailInUseException;
import com.lezrak.scrabblebackend.exceptionHandling.EmailNotVerified;
import com.lezrak.scrabblebackend.exceptionHandling.NicknameInUseException;
import com.lezrak.scrabblebackend.exceptionHandling.PlayerNotFoundException;
import com.lezrak.scrabblebackend.game.GameDTO;
import com.lezrak.scrabblebackend.game.GameMapper;
import com.lezrak.scrabblebackend.game.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, GameRepository gameRepository, VerificationTokenService verificationTokenService) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.verificationTokenService = verificationTokenService;
    }

    @Override
    public boolean checkCredentials(PlayerDTO playerDTO) {
        if(playerDTO.getEmail().length()>0 && emailIsUsed(playerDTO.getEmail())){
            if(!playerRepository.findPlayerByNickname(playerDTO.getNickname()).isEnabled())
                throw new EmailNotVerified(playerDTO.getEmail());
        }
        return playerRepository.existsPlayerByEmailAndNicknameAndPassword(
                playerDTO.getEmail(), playerDTO.getNickname(), playerDTO.getPassword());
    }


    @Override
    public PlayerDTO postPlayer(PlayerDTO playerDTO) throws MessagingException {
        if (nicknameIsUsed(playerDTO.getNickname())) {
            throw new NicknameInUseException(playerDTO.getNickname());
        }
        Player player = new Player(playerDTO.getNickname());
        if (playerDTO.getPassword().length() > 0) player.setPassword(playerDTO.getPassword());
        if (playerDTO.getEmail().length() > 0) {
            if (emailIsUsed(playerDTO.getEmail())) {
                throw new EmailInUseException(playerDTO.getEmail());
            }
            player.setEmail(playerDTO.getEmail());
            player = playerRepository.save(player);
            verificationTokenService.createToken(player);
        }
        else {
            player.enable();
            player = playerRepository.save(player);
        }
        return PlayerMapper.toPlayerDTO(player);
    }

    @Override
    public List<GameDTO> getGames(Long id) {
        if (!playerRepository.existsPlayerById(id)) throw new PlayerNotFoundException(id.toString());
        return GameMapper.toGameDTO(
                playerRepository.findPlayerById(id)
                        .getGames()
                        .stream()
                        .map(gameRepository::findByName)
                        .collect(Collectors.toList()));
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
