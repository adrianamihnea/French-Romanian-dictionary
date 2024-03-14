package com.dictionary.dictionary;

import com.dictionary.dictionary.model.Role;
import com.dictionary.dictionary.model.Translation;
import com.dictionary.dictionary.model.User;
import com.dictionary.dictionary.model.Word;
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
			Word word1 = new Word(null, "fenetre", null);
			Word word2 = new Word(null, "porte", null);
			Word word3 = new Word(null, "cerveau", null);
			ArrayList<Word> words = new ArrayList<>();
			words.add(word1);
			words.add(word2);
			words.add(word3);
			if(activeUser.getRoles().contains(create)) {
				wordRepository.saveAll(words);
			}
			else {
				System.out.println("Create: operation denied for user " + activeUser.getUsername());
			}

			Translation translation1 = new Translation(null, "fereastra", null);
			translation1.setWordInFrench(word1);
			Translation translation2 = new Translation(null, "geam", null);
			translation2.setWordInFrench(word1);
			Translation translation3 = new Translation(null, "usa", null);
			translation3.setWordInFrench(word2);
			ArrayList<Translation> translations = new ArrayList<>();
			translations.add(translation1);
			translations.add(translation2);
			translations.add(translation3);
			ArrayList<Translation> translationsForWord1 = new ArrayList<>();
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
			Optional<Translation> translation = translationRepository.findById(word1.getId());
			System.out.println("Translation for word " + word1.getWordInFrench() + " is " + translation.get().getWordInRomanian());
			System.out.println("Translation for word " + word1.getWordInFrench() + " is " +
					word1.getTranslations()
					.stream()
					.map(Translation::getWordInRomanian)
					.collect(Collectors.toList())
			);

			//update
			String modifiedString = "fenêtre";
			Iterable<Word> allWords = wordRepository.findAll();
			for(Word word : allWords) {
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
			for(Word word : allWords) {
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

