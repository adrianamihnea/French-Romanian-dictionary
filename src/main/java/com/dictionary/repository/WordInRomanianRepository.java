package com.dictionary.repository;

import com.dictionary.model.WordInRomanian;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordInRomanianRepository extends CrudRepository<WordInRomanian, Long> {
    List<WordInRomanian> findByWordInFrenchId(Long wordInFrenchId);
}
