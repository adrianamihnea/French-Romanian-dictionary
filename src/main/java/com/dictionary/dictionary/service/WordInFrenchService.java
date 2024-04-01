package com.dictionary.dictionary.service;

import com.dictionary.dictionary.exceptions.ApiExceptionsResponse;
import com.dictionary.dictionary.model.WordInFrench;
import com.dictionary.dictionary.model.WordInRomanian;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface WordInFrenchService {
    Optional<WordInFrench> findByTranslation(WordInRomanian wordInRomanian) throws ApiExceptionsResponse;

    WordInFrench updateTranslation(Long id, WordInRomanian translation);

    List<WordInFrench> findAll();
    WordInFrench findByID(Long id);
    WordInFrench deleteWord(Long id);
}
