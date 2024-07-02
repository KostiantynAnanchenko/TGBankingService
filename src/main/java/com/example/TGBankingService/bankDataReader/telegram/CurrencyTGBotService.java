package com.example.TGBankingService.bankDataReader.telegram;

import com.example.TGBankingService.bankDataReader.commands.Command;
import com.example.TGBankingService.bankDataReader.currencyParser.views.*;
import com.example.TGBankingService.bankDataReader.db.DataBase;
import com.example.TGBankingService.bankDataReader.dto.UsersDTO;
import com.example.TGBankingService.bankDataReader.enums.BanksName;
import com.example.TGBankingService.bankDataReader.enums.CurrencyEnum;
import org.apache.commons.lang3.EnumUtils;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;

public class CurrencyTGBotService extends TelegramLongPollingCommandBot {

    public CurrencyTGBotService() {

        register(new Command());
    }

    @Override
    public String getBotUsername() {
        return LoginConstants.NAME;
    }

    @Override
    public String getBotToken() {
        return LoginConstants.TOKEN;
    }

//    @Override
//    public void onUpdatesReceived(List<Update> updates) {
//        updates.stream().filter(u -> u.hasMessage()).map(u -> u.getMessage().getText() + " from " + u.getMessage().getChat().getUserName()).peek(System.out::println).collect(Collectors.toList());
//        System.out.println();
//    }


    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();

            handleInfoAndSettings(data, callbackQuery);
            handleBanks(data, callbackQuery);
            handleCurrency(data, callbackQuery);
            handleSettingsOptions(data, callbackQuery);
            handleDecimalPlaces(data, callbackQuery);
            handleNotification(data, callbackQuery);
        }
    }

    private void handleInfoAndSettings(String data, CallbackQuery callbackQuery) {
        if ("Отримати інфо".equals(data)) {
            Info.getInfoMethod(this, callbackQuery.getMessage().getChat());
        } else if ("Налаштування".equals(data)) {
            Settings.displaySettingsMethod(this, callbackQuery.getMessage().getChat());
        }
    }

    private void handleBanks(String data, CallbackQuery callbackQuery) {
        try (DataBase db = DataBase.getInstance()) {

            long userId = callbackQuery.getMessage().getChat().getId();

            UsersDTO userInfo = db.getUser((int) userId);
            List<String> banks = userInfo.getBank();

            if (BanksName.PRIVATBANK.toString().equals(data)) {
                Banks.oneChoice(BanksName.PRIVATBANK.toString(), banks);
                Banks.namesOfBanks(this, callbackQuery.getMessage().getChat());
            } else if (BanksName.MONOBANK.toString().equals(data)) {
                Banks.oneChoice(BanksName.MONOBANK.toString(), banks);
                Banks.namesOfBanks(this, callbackQuery.getMessage().getChat());

            } else if (BanksName.OSHCHADBANK.toString().equals(data)) {
                Banks.oneChoice(BanksName.OSHCHADBANK.toString(), banks);
                Banks.namesOfBanks(this, callbackQuery.getMessage().getChat());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCurrency(String data, CallbackQuery callbackQuery) {
        List<String> currencyList = getUserCurrencyList(callbackQuery);

        if (currencyList != null && EnumUtils.isValidEnum(CurrencyEnum.class, data)) {
            Currency.currencyChoice(data, currencyList);
            Currency.chooseCurrency(this, callbackQuery.getMessage().getChat());
        }
    }

    private List<String> getUserCurrencyList(CallbackQuery callbackQuery) {
        try (DataBase db = DataBase.getInstance()) {
            long userId = callbackQuery.getMessage().getChat().getId();
            UsersDTO userInfo = db.getUser((int) userId);
            return userInfo.getCurrency();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSettingsOptions(String data, CallbackQuery callbackQuery) {
        if ("Банк".equals(data)) {
            Banks.namesOfBanks(this, callbackQuery.getMessage().getChat());
        } else if ("Валюта".equals(data)) {
            Currency.chooseCurrency(this, callbackQuery.getMessage().getChat());
        } else if ("Кращі пропозиції".equals(data)) {
            // BestOffers.bestCurrencyOffers(this, callbackQuery.getMessage().getChat());
        } else if ("Час сповіщень".equals(data)) {
            NotificationsTime.setNotificationsTime(this, callbackQuery.getMessage().getChat());
        } else if ("Кількість знаків після коми".equals(data)) {
            DecimalPlaces.decimalPlacesMethod(this, callbackQuery.getMessage().getChat());
        }
    }

    private void handleDecimalPlaces(String data, CallbackQuery callbackQuery) {
        if (Arrays.asList("2", "3", "4").contains(data)) {
            try (DataBase db = DataBase.getInstance()) {
                long userId = callbackQuery.getMessage().getChat().getId();
                UsersDTO userInfo = db.getUser((int) userId);
                userInfo.setSymbols(Integer.parseInt(data));
                DecimalPlaces.decimalPlacesMethod(this, callbackQuery.getMessage().getChat());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleNotification(String data, CallbackQuery callbackQuery) {
        try (DataBase db = DataBase.getInstance()) {
            long userId = callbackQuery.getMessage().getChat().getId();
            UsersDTO userInfo = db.getUser((int) userId);

            if (Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00").contains(data)) {
                userInfo.setNotificationTime(Integer.parseInt(data.replaceAll(":00", "")));
                NotificationsTime.setNotificationsTime(this, callbackQuery.getMessage().getChat());
            } else if ("Вимкнути час сповіщення".equals(data)) {
                userInfo.setNotificationTime(-1);
            }

            String tmp = String.valueOf(userInfo.getNotificationTime()) + ":00";
            if (NotificationsTime.timer().equals(tmp)) {
                Info.getInfoMethod(this, callbackQuery.getMessage().getChat());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
