package com.dictionary.dictionary.service.impl;

import com.dictionary.dictionary.exceptions.ApiExceptionsResponse;
import com.dictionary.dictionary.model.WordInFrench;
import com.dictionary.dictionary.model.WordInRomanian;
import com.dictionary.dictionary.service.WordInFrenchService;
import com.dictionary.dictionary.repository.WordInFrenchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WordInFrenchServiceImpl implements WordInFrenchService {

    @Autowired
    private WordInFrenchRepository wordInFrenchRepository;
    @Override
    public Optional<WordInFrench> findByTranslation(WordInRomanian wordInRomanian) throws ApiExceptionsResponse {
       List<WordInFrench> words = (List<WordInFrench>) wordInFrenchRepository.findAll();
       for(WordInFrench word : words) {
           List<WordInRomanian> translations = word.getWordsInRomanian();
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
            wordInFrench.get().setWordsInRomanian(Collections.singletonList(translation));
        }
        wordInFrenchRepository.save(wordInFrench.get());
        return wordInFrench.get();
    }

    @Override
    public List<WordInFrench> findAll() {
        return (List<WordInFrench>) wordInFrenchRepository.findAll();
    }

    @Override
    public WordInFrench findByID(Long id) {
        return wordInFrenchRepository.findById(id).get();
    }

    @Override
    public WordInFrench deleteWord(Long id) {
        WordInFrench wordInFrench = wordInFrenchRepository.findById(id).get();
        wordInFrenchRepository.delete(wordInFrench);
        return wordInFrench;
    }
}
