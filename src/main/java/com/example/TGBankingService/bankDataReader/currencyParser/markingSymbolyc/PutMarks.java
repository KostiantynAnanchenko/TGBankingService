package com.example.TGBankingService.bankDataReader.currencyParser.markingSymbolyc;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class PutMarks<T> {

    public InlineKeyboardMarkup addButtons(List<T> buttonTexts, List<String> userParams) {
        return this.addButtons(buttonTexts, userParams, 1);
    }

    public InlineKeyboardMarkup addButtons(List<T> buttonTexts, List<String> userParams, int rows) {
        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder inlineKeyboardMarkup = InlineKeyboardMarkup.builder();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> buttonList = new ArrayList<>();

        int i = 0;
        for (T button : buttonTexts) {
            i += 1;

            String text = button.toString();
            String finalText;
            if (userParams.contains(text)) {
                finalText = Checkmark.addCheck(text);
            } else {
                finalText = text;
            }

            InlineKeyboardButton buttonObject = InlineKeyboardButton.builder().text(finalText).callbackData(text).build();
            buttonList.add(buttonObject);

            if (i % rows == 0) {
                rowsInline.add(buttonList);
                buttonList = new ArrayList<>();
            }
        }

        if (!buttonList.isEmpty()) {
            rowsInline.add(buttonList);
        }

        return inlineKeyboardMarkup.keyboard(rowsInline).build();
    }

}
