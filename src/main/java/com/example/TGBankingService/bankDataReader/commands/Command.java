package com.example.TGBankingService.bankDataReader.commands;


import com.example.TGBankingService.bankDataReader.currencyParser.views.Settings;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Command extends BotCommand {

    public Command() {
        super("start", "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage sendMessage = new SendMessage();
        String firstMsg = "Оберіть пункти з меню";
        sendMessage.setText(firstMsg);
        sendMessage.setChatId(chat.getId());

        InlineKeyboardButton info = InlineKeyboardButton.builder().text("Отримати інфо").callbackData("Отримати інфо").build();
        InlineKeyboardButton settings = InlineKeyboardButton.builder().text("Налаштування").callbackData("Налаштування").build();
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder().keyboard(Collections.singletonList(Arrays.asList(info, settings))).build();
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("Something wrong :(");
        }

        List<List<InlineKeyboardButton>> keyboard = inlineKeyboardMarkup.getKeyboard();
        if (!keyboard.isEmpty() && keyboard.get(0).size() == 2) {
            String buttonTextInfo = keyboard.get(0).get(0).getText();
            String buttonTextSettings = keyboard.get(0).get(1).getText();

            if ("Отримати інфо".equals(buttonTextInfo)) {
//                Info.getInfoMethod(absSender, chat);
            } else if ("Налаштування".equals(buttonTextSettings)) {
                Settings.displaySettingsMethod(absSender, chat);
            }
        }
    }
}


