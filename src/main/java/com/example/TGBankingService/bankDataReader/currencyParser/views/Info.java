package com.example.TGBankingService.bankDataReader.currencyParser.views;

import com.example.TGBankingService.bankDataReader.telegram.TelegramPrettyPrinter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Info {

    public static void getInfoMethod(AbsSender absSender, Chat chat) {
        SendMessage sendMessage = new SendMessage();
        String titleMessage = TelegramPrettyPrinter.resultDataForPrint(chat);
        sendMessage.setText(titleMessage);
        sendMessage.setChatId(chat.getId());

        try {
            absSender.execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace(System.out);
            System.out.println("Something wrong with sending settings message :(");
        }
    }

}
