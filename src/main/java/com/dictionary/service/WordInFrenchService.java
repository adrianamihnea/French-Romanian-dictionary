package com.dictionary.service;

import com.dictionary.exceptions.ApiExceptionsResponse;
import com.dictionary.model.WordInFrench;
import com.dictionary.model.WordInRomanian;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface WordInFrenchService {
    Optional<WordInFrench> findByTranslation(WordInRomanian wordInRomanian) throws ApiExceptionsResponse;

    WordInFrench updateTranslation(Long id, WordInRomanian translation);

    List<WordInFrench> findAll();
    Optional<WordInFrench> findByID(Long id);
    void deleteWord(Long id);

    WordInFrench addWord(WordInFrench word);

    Optional<WordInFrench> findById(Long id);

    WordInFrench updateWord(Long id, WordInFrench updatedWord);

    List<WordInRomanian> findTranslationsByWordId(Long id);
}
