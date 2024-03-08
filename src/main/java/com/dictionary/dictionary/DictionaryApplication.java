package com.dictionary.dictionary;

import com.dictionary.dictionary.model.Translation;
import com.dictionary.dictionary.model.Word;
import com.dictionary.dictionary.repository.TranslationRepository;
import com.dictionary.dictionary.repository.WordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

@SpringBootApplication
public class DictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DictionaryApplication.class, args);
	}

	@Bean
	CommandLineRunner init(WordRepository wordRepository, TranslationRepository translationRepository) {
		return args -> {
			Word word1 = new Word(null, "fenetre", null);
			Word word2 = new Word(null, "porte", null);

			ArrayList<Word> words = new ArrayList<>();
			words.add(word1);
			words.add(word2);
			wordRepository.saveAll(words);

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
			translationRepository.saveAll(translations);
			wordRepository.saveAll(words);


		};

	}
}

