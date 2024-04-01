package com.dictionary.dictionary.repository;

import com.dictionary.dictionary.model.WordInRomanian;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldInRomanianRepository extends CrudRepository<WordInRomanian, Long> {
    WordInRomanian findFirstByWordInRomanian(String wordInRomanian);
}
