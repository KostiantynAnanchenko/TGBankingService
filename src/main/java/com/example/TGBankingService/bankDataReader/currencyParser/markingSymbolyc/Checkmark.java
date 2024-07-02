package com.example.TGBankingService.bankDataReader.currencyParser.markingSymbolyc;

public class Checkmark {
    /**
     * метод addCheck додає "✔" до назви кнопки
     *
     * @param buttonName
     * @return
     */
    public static String addCheck(String buttonName) {
        if (!buttonName.endsWith("✔")) {
            buttonName = buttonName + " ✔";
        }
        return buttonName;
    }
}
