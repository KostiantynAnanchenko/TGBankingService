package com.example.TGBankingService.bankDataReader.currencyParser.views;

import com.example.TGBankingService.bankDataReader.currencyParser.markingSymbolyc.PutMarks;
import com.example.TGBankingService.bankDataReader.db.DataBase;
import com.example.TGBankingService.bankDataReader.dto.UsersDTO;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotificationsTime {

    public static String timer() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        return dtf.format(LocalDateTime.now());
    }

    public static String timeToday() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm"));
    }

    public static void setNotificationsTime(AbsSender absSender, Chat chat) {
        SendMessage sendMessage = new SendMessage();
        String titleMessage = "Оберіть час сповіщень";
        sendMessage.setText(titleMessage);
        sendMessage.setChatId(chat.getId());

        try (DataBase db = DataBase.getInstance()) {
            UsersDTO userInfo = db.getUser(Math.toIntExact(chat.getId()));

            int notificationTime = userInfo.getNotificationTime();

            PutMarks<String> putClass = new PutMarks<>();
            sendMessage.setReplyMarkup(putClass.addButtons(List.of("09:00", "10:00", "11:00", "12:00", "13:00",
                            "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "Вимкнути час сповіщення"),
                    List.of(notificationTime + ":00"), 3));

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

    public static void main(String[] args) {
        System.out.println(timeToday());
    }
}
