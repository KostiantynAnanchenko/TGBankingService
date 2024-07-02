package com.example.TGBankingService;

import com.example.TGBankingService.bankDataReader.telegram.TelegramBotService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TgBankingServiceApplication {

    public static void main(String[] args) {
        TelegramBotService telegramBotService = new TelegramBotService();
    }
}
