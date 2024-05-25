package com.dictionary.service;

import com.dictionary.model.WordInFrench;
import com.dictionary.repository.WordInFrenchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryExportService {

    private final WordInFrenchRepository wordInFrenchRepository;

    @Autowired
    public DictionaryExportService(WordInFrenchRepository wordInFrenchRepository) {
        this.wordInFrenchRepository = wordInFrenchRepository;
    }

    public Iterable<WordInFrench> getAllWords() {
        return wordInFrenchRepository.findAll();
    }
}
