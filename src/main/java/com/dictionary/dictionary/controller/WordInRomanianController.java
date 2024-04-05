package com.dictionary.dictionary.controller;

import com.dictionary.dictionary.model.WordInRomanian;
import com.dictionary.dictionary.service.WordInFrenchService;
import com.dictionary.dictionary.service.WordInRomanianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class WordInRomanianController {

    @Autowired
    private WordInFrenchService wordInFrenchService;
    @Autowired
    private WordInRomanianService wordInRomanianService;

    @GetMapping("/words/{id}/translations")
    public List<WordInRomanian> getTranslations(@PathVariable Long id) {
        return wordInFrenchService.findTranslationsByWordId(id);
    }

    @PostMapping("/words/{id}/translations/new")
    public WordInRomanian addTranslation(@PathVariable Long id, @RequestBody WordInRomanian translation) {
        return wordInRomanianService.addTranslation(id, translation);
    }

    @PutMapping("/words/{id}/translations/{translationId}/edit")
    public WordInRomanian updateTranslation(@PathVariable Long id, @PathVariable Long translationId, @RequestBody WordInRomanian updatedTranslation) {
        return wordInRomanianService.updateTranslation(id, translationId, updatedTranslation);
    }
    // Add other methods as needed

}
