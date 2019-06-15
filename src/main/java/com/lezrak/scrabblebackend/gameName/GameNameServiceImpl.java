package com.lezrak.scrabblebackend.gameName;

import com.lezrak.scrabblebackend.game.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GameNameServiceImpl implements GameNameService {

    private static int LOADED_NAMES = 0;

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
        String name = gameNameRepository.findGameNameById(random.nextInt(LOADED_NAMES)).getName();
        while (gameRepository.findByName(name) != null) {
            name += random.nextInt(10);
        }
        return name;
    }


    @Override
    @EventListener(ApplicationReadyEvent.class)
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
                        LOADED_NAMES+=329;
                        list.clear();
                        System.out.println("Populating database - currently at: "+line);
                    }
                }
                in.close();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
