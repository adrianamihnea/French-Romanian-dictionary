package com.dictionary.dictionary.repository;

import com.dictionary.dictionary.model.Translation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepository extends CrudRepository<Translation, Long> {
    Translation findFirstByWordInRomanian(String wordInRomanian);
}
