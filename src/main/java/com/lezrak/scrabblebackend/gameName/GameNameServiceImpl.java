package com.lezrak.scrabblebackend.gameName;

import com.lezrak.scrabblebackend.game.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
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
        // there is 2965277 words allowed in polish version of scrabble, all saved in gameNameRepository
        String name = gameNameRepository.findGameNameById(random.nextInt(2965277)).getName();
        while (gameRepository.findByName(name) != null) {
            name += random.nextInt(10);
        }
        return name;
    }


    @Override
    @PostConstruct
    public  void populate() {
        if (gameNameRepository.findAll().size() == 0) {
            try {
                InputStream fStream = new ClassPathResource("slowa.txt").getInputStream();
                DataInputStream in = new DataInputStream(fStream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                ArrayList<String> list = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    list.add(line);
                    //there is 2965277 words on the list, 329 is divider of 2965277, which allows us to use batching and save all words from list
                    if (list.size() == 329) {
                        gameNameRepository.saveAll(list.stream().map(GameName::new).collect(Collectors.toList()));
                        list.clear();
                    }
                }
                in.close();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
