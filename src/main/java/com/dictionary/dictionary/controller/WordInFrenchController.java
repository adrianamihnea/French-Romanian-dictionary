package com.dictionary.dictionary.controller;

import com.dictionary.dictionary.model.WordInFrench;
import com.dictionary.dictionary.model.WordInRomanian;
import com.dictionary.dictionary.service.WordInFrenchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/words")
public class WordInFrenchController {

    @Autowired
    private WordInFrenchService wordInFrenchService;

    // Get all words
    @GetMapping
    public List<WordInFrench> getAllWords() {
        return wordInFrenchService.findAll();
    }

    // Get word by ID
    @GetMapping("/{id}")
    public WordInFrench getWord(@PathVariable Long id) {
        return wordInFrenchService.findById(id).orElseThrow(RuntimeException::new);
    }

    // Add a new word
    @PostMapping("/new")
    public ResponseEntity addWord(@RequestBody WordInFrench word) throws URISyntaxException {
        WordInFrench savedWord = wordInFrenchService.addWord(word);
        return ResponseEntity.created(new URI("/words/" + savedWord.getId())).body(savedWord);
    }

    // Update a word
    @PutMapping("/{id}")
    public ResponseEntity updateWord(@PathVariable Long id, @RequestBody WordInFrench updatedWord) {
        WordInFrench currentWord = wordInFrenchService.updateWord(id, updatedWord);
        return ResponseEntity.ok(currentWord);
    }

    // Delete a word
    @DeleteMapping("/{id}")
    public ResponseEntity deleteWord(@PathVariable Long id) {
        wordInFrenchService.deleteWord(id);
        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).build();
    }

//    @GetMapping("/{id}/translations")
//    public List<WordInRomanian> getTranslations(@PathVariable Long id) {
//        return wordInFrenchService.findTranslationsByWordId(id);
//    }
}
