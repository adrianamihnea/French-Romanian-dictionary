package com.dictionary.repository;

import com.dictionary.model.WordInFrench;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordInFrenchRepository extends CrudRepository<WordInFrench, Long> {
    Iterable<WordInFrench> findAll();
}
