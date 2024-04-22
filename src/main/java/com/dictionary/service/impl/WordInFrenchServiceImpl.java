package com.dictionary.service.impl;

import com.dictionary.exceptions.ApiExceptionsResponse;
import com.dictionary.model.WordInFrench;
import com.dictionary.model.WordInRomanian;
import com.dictionary.repository.WordInRomanianRepository;
import com.dictionary.service.WordInFrenchService;
import com.dictionary.repository.WordInFrenchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class WordInFrenchServiceImpl implements WordInFrenchService {

    @Autowired
    private WordInFrenchRepository wordInFrenchRepository;
    @Autowired
    private WordInRomanianRepository wordInRomanianRepository;
    @Override
    public Optional<WordInFrench> findByTranslation(WordInRomanian wordInRomanian) throws ApiExceptionsResponse {
       List<WordInFrench> words = (List<WordInFrench>) wordInFrenchRepository.findAll();
       for(WordInFrench word : words) {
           List<WordInRomanian> translations = word.getTranslations();
           if(translations.contains(wordInRomanian)) {
               return Optional.of(word);
           }
       }
        return Optional.empty();
    }

    @Override
    public WordInFrench updateTranslation(Long id, WordInRomanian translation) {
        Optional<WordInFrench> wordInFrench = wordInFrenchRepository.findById(id);
        if(wordInFrench.isPresent()) {
            wordInFrench.get().setTranslations(Collections.singletonList(translation));
        }
        wordInFrenchRepository.save(wordInFrench.get());
        return wordInFrench.get();
    }

    @Override
    public List<WordInFrench> findAll() {
        return (List<WordInFrench>) wordInFrenchRepository.findAll();
    }

    @Override
    public Optional<WordInFrench> findByID(Long id) {
        return Optional.of(wordInFrenchRepository.findById(id).get());
    }

    @Override
    @Transactional
    public void deleteWord(Long id) {
        Optional<WordInFrench> optionalWord = wordInFrenchRepository.findById(id);
        if (optionalWord.isPresent()) {
            // Get the word in French
            WordInFrench wordInFrench = optionalWord.get();
            // Delete associated records in the word_in_romanian table
            List<WordInRomanian> wordInRomanianList = wordInFrench.getTranslations();
            if (wordInRomanianList != null && !wordInRomanianList.isEmpty()) {
                wordInRomanianRepository.deleteAll(wordInRomanianList);
            }
            // Delete the word in French
            wordInFrenchRepository.delete(wordInFrench);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Word not found with id: " + id);
        }
    }

    @Override
    public WordInFrench addWord(WordInFrench word) {
        return wordInFrenchRepository.save(word);
    }

    @Override
    public Optional<WordInFrench> findById(Long id) {
        return wordInFrenchRepository.findById(id);
    }

    @Override
    public WordInFrench updateWord(Long id, WordInFrench updatedWord) {
        // Retrieve the existing word by id
        Optional<WordInFrench> optionalWord = wordInFrenchRepository.findById(id);
        if (optionalWord.isPresent()) {
            // Update the word with the values from updatedWord
            WordInFrench existingWord = optionalWord.get();
            existingWord.setWordInFrench(updatedWord.getWordInFrench());
            // You can update other fields as well if needed
            // Save the updated word
            return wordInFrenchRepository.save(existingWord);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Word not found with id: " + id);
        }
    }

    @Override
    public List<WordInRomanian> findTranslationsByWordId(Long id) {
        Optional<WordInFrench> wordInFrenchOptional = wordInFrenchRepository.findById(id);
        if (wordInFrenchOptional.isPresent()) {
            WordInFrench wordInFrench = wordInFrenchOptional.get();
            return wordInFrench.getTranslations();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Word not found with id: " + id);
        }
    }

    @Override
    public String selectRandomWord() {
        // Retrieve all words from the French dictionary
        Iterable<WordInFrench> words = wordInFrenchRepository.findAll();

        /// Convert Iterable to a list for easier manipulation
        List<WordInFrench> wordList = (List<WordInFrench>) words;

        // Check if the list of words is not empty
        if (!wordList.isEmpty()) {
            // Generate a random index to select a word from the list
            Random random = new Random();
            int randomIndex = random.nextInt(wordList.size());

            // Return the randomly selected word
            return wordList.get(randomIndex).getWordInFrench();
        } else {
            // If the list is empty, return a default word or handle the situation accordingly
            return "No words found in the dictionary";
        }
    }
}
