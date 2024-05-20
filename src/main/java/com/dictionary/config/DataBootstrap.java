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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public DataBootstrap(UserRepository userRepository, RoleRepository roleRepository,
                         WordInFrenchRepository wordInFrenchRepository,
                         WordInRomanianRepository wordInRomanianRepository,
                         BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.wordInFrenchRepository = wordInFrenchRepository;
        this.wordInRomanianRepository = wordInRomanianRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Initialize roles
        Role adminRole = roleRepository.save(new Role(null, "ADMIN"));
        Role userRole = roleRepository.save(new Role(null, "USER"));

        // Initialize users with hashed passwords
        User adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role(adminRole)
                .loggedIn(false)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User normalUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .role(userRole)
                .loggedIn(false)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

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
