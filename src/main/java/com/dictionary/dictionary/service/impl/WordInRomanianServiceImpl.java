package com.dictionary.dictionary.service.impl;

import com.dictionary.dictionary.model.WordInRomanian;
import com.dictionary.dictionary.repository.WordInFrenchRepository;
import com.dictionary.dictionary.repository.WordInRomanianRepository;
import com.dictionary.dictionary.service.WordInRomanianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordInRomanianServiceImpl implements WordInRomanianService {

    @Autowired
    private WordInRomanianRepository wordInRomanianRepository;
    @Autowired
    private WordInFrenchRepository wordInFrenchRepository;

    @Override
    public List<WordInRomanian> findAllByWordInFrenchId(Long id) {
        return wordInRomanianRepository.findByWordInFrenchId(id);
    }

    @Override
    public WordInRomanian addTranslation(Long id, WordInRomanian translation) {
        return wordInFrenchRepository.findById(id).map(wordInFrench -> {
            translation.setWordInFrench(wordInFrench);
            return wordInRomanianRepository.save(translation);
        }).orElseThrow(() -> new IllegalArgumentException("Word in French not found with id: " + id));
    }

    @Override
    public WordInRomanian updateTranslation(Long wordInFrenchId, Long translationId, WordInRomanian updatedTranslation) {
        // Check if the translation exists
        return wordInRomanianRepository.findById(translationId).map(translation -> {
            // Check if the translation belongs to the specified word in French
            if (translation.getWordInFrench().getId().equals(wordInFrenchId)) {
                // Update the translation
                translation.setWordInRomanian(updatedTranslation.getWordInRomanian());
                // Save and return the updated translation
                return wordInRomanianRepository.save(translation);
            } else {
                throw new IllegalArgumentException("Translation with id " + translationId + " does not belong to word in French with id " + wordInFrenchId);
            }
        }).orElseThrow(() -> new IllegalArgumentException("Translation with id " + translationId + " not found"));
    }
}
