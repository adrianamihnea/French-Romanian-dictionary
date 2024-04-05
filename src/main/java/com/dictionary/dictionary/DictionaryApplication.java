package com.dictionary.dictionary;

import com.dictionary.dictionary.model.Role;
import com.dictionary.dictionary.model.WordInRomanian;
import com.dictionary.dictionary.model.User;
import com.dictionary.dictionary.model.WordInFrench;
import com.dictionary.dictionary.repository.RoleRepository;
import com.dictionary.dictionary.repository.WordInRomanianRepository;
import com.dictionary.dictionary.repository.UserRepository;
import com.dictionary.dictionary.repository.WordInFrenchRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class DictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DictionaryApplication.class, args);
	}

	@Bean
	CommandLineRunner init(WordInFrenchRepository wordRepository, WordInRomanianRepository translationRepository,
						   UserRepository userRepository, RoleRepository roleRepository) {
		return args -> {

			//roles
			Role create = new Role(null, "create");
			Role retrieve = new Role(null, "retrieve");
			Role update = new Role(null, "update");
			Role delete = new Role(null, "delete");
			List<Role> rolesForAdmin = new ArrayList<>();
			rolesForAdmin.add(create);
			rolesForAdmin.add(retrieve);
			rolesForAdmin.add(update);
			rolesForAdmin.add(delete);
			List<Role> rolesForUser = new ArrayList<>();
			rolesForUser.add(create);
			rolesForUser.add(retrieve);
			roleRepository.saveAll(rolesForAdmin);

			// users
			User admin = new User(null, "admin", "admin123", rolesForAdmin, null,
					null, null, null, null, null,
					null, null);
			userRepository.save(admin);
			User user = new User(null, "user", "user123", rolesForUser, null,
					null, null, null, null, null,
					null, null);
			userRepository.save(user);

			// ACTIVE USER
			User activeUser = admin;
			// end of ACTIVE USER

			// create
			WordInFrench word1 = new WordInFrench(null, "fenetre", null);
			WordInFrench word2 = new WordInFrench(null, "porte", null);
			WordInFrench word3 = new WordInFrench(null, "cerveau", null);
			ArrayList<WordInFrench> words = new ArrayList<>();
			words.add(word1);
			words.add(word2);
			words.add(word3);
			if(activeUser.getRoles().contains(create)) {
				wordRepository.saveAll(words);
			}
			else {
				System.out.println("Create: operation denied for user " + activeUser.getUsername());
			}

			WordInRomanian translation1 = new WordInRomanian(null, "fereastra", null);
			translation1.setWordInFrench(word1);
			WordInRomanian translation2 = new WordInRomanian(null, "geam", null);
			translation2.setWordInFrench(word1);
			WordInRomanian translation3 = new WordInRomanian(null, "usa", null);
			translation3.setWordInFrench(word2);
			ArrayList<WordInRomanian> translations = new ArrayList<>();
			translations.add(translation1);
			translations.add(translation2);
			translations.add(translation3);
			ArrayList<WordInRomanian> translationsForWord1 = new ArrayList<>();
			translationsForWord1.add(translation1);
			translationsForWord1.add(translation2);
			word1.setTranslations(translationsForWord1);
			word2.setTranslations(Collections.singletonList(translation3));
			if(activeUser.getRoles().contains(create)) {
				translationRepository.saveAll(translations);
				wordRepository.saveAll(words);
			}
			else {
				System.out.println("Create: operation denied for user " + activeUser.getUsername());
			}

			// retrieve
			Optional<WordInRomanian> translation = translationRepository.findById(word1.getId());
			System.out.println("Translation for word " + word1.getWordInFrench() + " is " + translation.get().getWordInRomanian());
			System.out.println("Translation for word " + word1.getWordInFrench() + " is " +
					word1.getTranslations()
							.stream()
							.map(WordInRomanian::getWordInRomanian)
							.collect(Collectors.toList())
			);

			//update
			String modifiedString = "fenêtre";
			Iterable<WordInFrench> allWords = wordRepository.findAll();
			for(WordInFrench word : allWords) {
				if(word.getWordInFrench().equals("fenetre")) {
					word.setWordInFrench(modifiedString);
					if(activeUser.getRoles().contains(update)) {
						wordRepository.save(word);
					}
					else {
						System.out.println("Update: operation denied for user " + activeUser.getUsername());
					}
				}
			}

			// delete
			String wordToBeDeleted = "cerveau";
			allWords = wordRepository.findAll();
			for(WordInFrench word : allWords) {
				if(word.getWordInFrench().equals(wordToBeDeleted)) {
					if(activeUser.getRoles().contains(delete)) {
						wordRepository.delete(word);
					}
					else {
						System.out.println("Delete: operation denied for user " + activeUser.getUsername());
					}
				}
			}
		};

	}
}
