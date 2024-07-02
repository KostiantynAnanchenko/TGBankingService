package com.example.TGBankingService.bankDataReader.currencyParser.views;

import com.example.TGBankingService.bankDataReader.currencyParser.markingSymbolyc.PutMarks;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    public static void displaySettingsMethod(AbsSender absSender, Chat chat) {
        SendMessage sendMessage = new SendMessage();
        String titleMessage = "Оберіть пункт налаштування";
        sendMessage.setText(titleMessage);
        sendMessage.setChatId(chat.getId());

        PutMarks<String> putClass = new PutMarks<>();
        sendMessage.setReplyMarkup(putClass.addButtons(List.of("Банк",
                "Валюта",
                "Кількість знаків після коми",
                "Час сповіщень"), new ArrayList<>()));


        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace(System.out);
            System.out.println("Something wrong with sending settings message :(");
        }
    }
}
