package com.dictionary.scheduler;

import com.dictionary.service.WebSocketService;
import com.dictionary.service.WordInFrenchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WordOfTheDayScheduler {

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private WordInFrenchService wordInFrenchService;

    @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    public void selectWordOfTheDay() {
        String wordOfTheDay = wordInFrenchService.selectRandomWord();
        webSocketService.broadcastWordOfTheDay(wordOfTheDay);
    }
}