package com.lezrak.scrabblebackend.gameName;

import com.lezrak.scrabblebackend.game.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameNameServiceImpl implements GameNameService {

    private final GameNameRepository gameNameRepository;

    private final GameRepository gameRepository;

    @Autowired
    public GameNameServiceImpl(GameNameRepository gameNameRepository, GameRepository gameRepository) {
        this.gameNameRepository = gameNameRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public String generateName() {
        Random random = new Random();
        String name = gameNameRepository.findGameNameById(random.nextInt(2965277)).getName();
        while (gameRepository.findByName(name) != null) {
            name += random.nextInt(10);
        }
        return name;
    }


    @Override
    public void populate() {
        if (gameNameRepository.findAll().size() == 0) {
            try {
                InputStream fStream = new ClassPathResource("slowa.txt").getInputStream();
                DataInputStream in = new DataInputStream(fStream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                gameNameRepository.saveAll(br.lines().map(GameName::new).collect(Collectors.toList()));
                in.close();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
