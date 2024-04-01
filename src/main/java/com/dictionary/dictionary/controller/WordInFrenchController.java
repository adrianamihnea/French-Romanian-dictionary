package com.dictionary.dictionary.controller;

import com.dictionary.dictionary.service.WordInFrenchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/all-words")
public class WordInFrenchController {
    @Autowired
    private WordInFrenchService wordInFrenchService;
}
