package com.example.TGBankingService.bankDataReader.currencyParser.views;

import com.example.TGBankingService.bankDataReader.currencyParser.markingSymbolyc.PutMarks;
import com.example.TGBankingService.bankDataReader.db.DataBase;
import com.example.TGBankingService.bankDataReader.dto.UsersDTO;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class DecimalPlaces {

    public static void decimalPlacesMethod(AbsSender absSender, Chat chat) {
        SendMessage sendMessage = new SendMessage();
        String titleMessage = "Оберіть кількість символів після коми";
        sendMessage.setText(titleMessage);
        sendMessage.setChatId(chat.getId());

        try (DataBase db = DataBase.getInstance()){
            UsersDTO userInfo = db.getUser(Math.toIntExact(chat.getId()));

            int afterComa = userInfo.getSymbols();
            PutMarks<String> putClass = new PutMarks<>();
            sendMessage.setReplyMarkup(putClass.addButtons(List.of("2",
                    "3",
                    "4"), List.of(String.valueOf(afterComa))));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace(System.out);
            System.out.println("Something wrong with sending settings message :(");
        }
    }

}
