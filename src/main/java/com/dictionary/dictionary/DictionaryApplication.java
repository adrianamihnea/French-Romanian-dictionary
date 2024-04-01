package com.dictionary.dictionary;

import com.dictionary.dictionary.model.Role;
import com.dictionary.dictionary.model.WordInRomanian;
import com.dictionary.dictionary.model.User;
import com.dictionary.dictionary.model.WordInFrench;
import com.dictionary.dictionary.repository.RoleRepository;
import com.dictionary.dictionary.repository.TranslationRepository;
import com.dictionary.dictionary.repository.UserRepository;
import com.dictionary.dictionary.repository.WordRepository;
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
	CommandLineRunner init(WordRepository wordRepository, TranslationRepository translationRepository,
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
			User activeUser = user;
			// end of ACTIVE USER

			// create
			WordInFrench wordInFrench1 = new WordInFrench(null, "fenetre", null);
			WordInFrench wordInFrench2 = new WordInFrench(null, "porte", null);
			WordInFrench wordInFrench3 = new WordInFrench(null, "cerveau", null);
			ArrayList<WordInFrench> wordInFrenches = new ArrayList<>();
			wordInFrenches.add(wordInFrench1);
			wordInFrenches.add(wordInFrench2);
			wordInFrenches.add(wordInFrench3);
			if(activeUser.getRoles().contains(create)) {
				wordRepository.saveAll(wordInFrenches);
			}
			else {
				System.out.println("Create: operation denied for user " + activeUser.getUsername());
			}

			WordInRomanian wordInRomanian1 = new WordInRomanian(null, "fereastra", null);
			wordInRomanian1.setWordsInFrench(Collections.singletonList(wordInFrench1));
			WordInRomanian wordInRomanian2 = new WordInRomanian(null, "geam", null);
			wordInRomanian2.setWordsInFrench(Collections.singletonList(wordInFrench1));
			WordInRomanian wordInRomanian3 = new WordInRomanian(null, "usa", null);
			wordInRomanian3.setWordsInFrench(Collections.singletonList(wordInFrench2));
			ArrayList<WordInRomanian> wordInRomanians = new ArrayList<>();
			wordInRomanians.add(wordInRomanian1);
			wordInRomanians.add(wordInRomanian2);
			wordInRomanians.add(wordInRomanian3);
			ArrayList<WordInRomanian> translationsForWord1 = new ArrayList<>();
			translationsForWord1.add(wordInRomanian1);
			translationsForWord1.add(wordInRomanian2);
			wordInFrench1.setWordInRomanians(translationsForWord1);
			wordInFrench2.setWordInRomanians(Collections.singletonList(wordInRomanian3));
			if(activeUser.getRoles().contains(create)) {
				translationRepository.saveAll(wordInRomanians);
				wordRepository.saveAll(wordInFrenches);
			}
			else {
				System.out.println("Create: operation denied for user " + activeUser.getUsername());
			}

			// retrieve
			Optional<WordInRomanian> translation = translationRepository.findById(wordInFrench1.getId());
			System.out.println("WordInRomanian for word " + wordInFrench1.getWordInFrench() + " is " + translation.get().getWordInRomanian());
			System.out.println("WordInRomanian for word " + wordInFrench1.getWordInFrench() + " is " +
					wordInFrench1.getWordInRomanians()
					.stream()
					.map(WordInRomanian::getWordInRomanian)
					.collect(Collectors.toList())
			);

			//update
			String modifiedString = "fenêtre";
			Iterable<WordInFrench> allWords = wordRepository.findAll();
			for(WordInFrench wordInFrench : allWords) {
				if(wordInFrench.getWordInFrench().equals("fenetre")) {
					wordInFrench.setWordInFrench(modifiedString);
					if(activeUser.getRoles().contains(update)) {
						wordRepository.save(wordInFrench);
					}
					else {
						System.out.println("Update: operation denied for user " + activeUser.getUsername());
					}
				}
			}

			// delete
			String wordToBeDeleted = "cerveau";
			allWords = wordRepository.findAll();
			for(WordInFrench wordInFrench : allWords) {
				if(wordInFrench.getWordInFrench().equals(wordToBeDeleted)) {
					if(activeUser.getRoles().contains(delete)) {
						wordRepository.delete(wordInFrench);
					}
					else {
						System.out.println("Delete: operation denied for user " + activeUser.getUsername());
					}
				}
			}
		};

	}
}

