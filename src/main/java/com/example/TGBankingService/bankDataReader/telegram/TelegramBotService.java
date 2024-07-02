package com.example.TGBankingService.bankDataReader.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotService {

    CurrencyTGBotService currencyTGBotService;

    public TelegramBotService() {
        currencyTGBotService = new CurrencyTGBotService();

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyTGBotService);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
