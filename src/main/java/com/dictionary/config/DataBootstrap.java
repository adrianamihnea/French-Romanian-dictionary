package com.dictionary.config;

import com.dictionary.model.Role;
import com.dictionary.model.User;
import com.dictionary.model.WordInFrench;
import com.dictionary.model.WordInRomanian;
import com.dictionary.repository.RoleRepository;
import com.dictionary.repository.UserRepository;
import com.dictionary.repository.WordInFrenchRepository;
import com.dictionary.repository.WordInRomanianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Component
public class DataBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final WordInFrenchRepository wordInFrenchRepository;
    private final WordInRomanianRepository wordInRomanianRepository;

    @Autowired
    public DataBootstrap(UserRepository userRepository, RoleRepository roleRepository,
                         WordInFrenchRepository wordInFrenchRepository,
                         WordInRomanianRepository wordInRomanianRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.wordInFrenchRepository = wordInFrenchRepository;
        this.wordInRomanianRepository = wordInRomanianRepository;
    }

    @Override
    public void run(String... args) {
        // Initialize roles
        Role adminRole = roleRepository.save(new Role(null, "ADMIN"));
        Role userRole = roleRepository.save(new Role(null, "USER"));

        // Initialize users
        User adminUser = new User(null, "admin", "admin123", Collections.singletonList(adminRole), null, null, null, null, null, null, null, null);
        User normalUser = new User(null, "user", "user123", Collections.singletonList(userRole), null, null, null, null, null, null, null, null);
        userRepository.saveAll(List.of(adminUser, normalUser));

        // Initialize words and translations from CSV
        initializeWordsFromCSV();
    }

    private void initializeWordsFromCSV() {
        String csvFile = Paths.get("C:", "GitHub", "French-romanian dictionary", "french_to_romanian.csv").toString();
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                String frenchWord = line[0];
                String romanianTranslation = line[1];

                // Create and save WordInFrench entity
                WordInFrench frenchEntity = wordInFrenchRepository.save(new WordInFrench(null, frenchWord, null));

                // Create and save WordInRomanian entity with the corresponding French entity
                WordInRomanian romanianEntity = new WordInRomanian(null, romanianTranslation, null);
                romanianEntity.setWordInFrench(frenchEntity);
                wordInRomanianRepository.save(romanianEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
