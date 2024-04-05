package com.dictionary.dictionary.service.impl;

import com.dictionary.dictionary.exceptions.ApiExceptionsResponse;
import com.dictionary.dictionary.model.WordInFrench;
import com.dictionary.dictionary.model.WordInRomanian;
import com.dictionary.dictionary.repository.WordInRomanianRepository;
import com.dictionary.dictionary.service.WordInFrenchService;
import com.dictionary.dictionary.repository.WordInFrenchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
}
