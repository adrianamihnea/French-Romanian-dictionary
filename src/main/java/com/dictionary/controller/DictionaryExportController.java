package com.dictionary.controller;

import com.dictionary.model.WordInFrench;
import com.dictionary.service.DictionaryExportService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/export")
public class DictionaryExportController {

    private final DictionaryExportService dictionaryExportService;

    @Autowired
    public DictionaryExportController(DictionaryExportService dictionaryExportService) {
        this.dictionaryExportService = dictionaryExportService;
    }

    @GetMapping("/xml")
    public String exportDictionaryToXml() {
        try {
            List<WordInFrench> words = (List<WordInFrench>) dictionaryExportService.getAllWords();
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
            return xmlMapper.writeValueAsString(words);
        } catch (IOException e) {
            return "Failed to export dictionary to XML: " + e.getMessage();
        }
    }
}
