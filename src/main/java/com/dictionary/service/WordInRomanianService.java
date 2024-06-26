package com.dictionary.service;

import com.dictionary.model.WordInRomanian;

import java.util.List;

public interface WordInRomanianService {
    List<WordInRomanian> findAllByWordInFrenchId(Long id);

    WordInRomanian addTranslation(Long id, WordInRomanian translation);

    WordInRomanian updateTranslation(Long id, Long translationId, WordInRomanian updatedTranslation);
}
