package com.example.TGBankingService.bankDataReader.db;

import com.example.TGBankingService.bankDataReader.dto.UsersDTO;
import com.example.TGBankingService.bankDataReader.enums.BanksName;
import com.example.TGBankingService.bankDataReader.enums.CurrencyEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    public UsersDTO getUser(int userId) {
        Map<Integer, UsersDTO> jsonData = DataBase.getInstance().getJsonData();

        if (!jsonData.containsKey(userId)) {
            jsonData.put(userId, new UsersDTO(
                    new ArrayList<>(List.of(BanksName.MONOBANK.toString())),
                    new ArrayList<>(List.of(CurrencyEnum.USD.toString())),
                    12,
                    2
            ));
        }

        return jsonData.get(userId);
    }

    public void updateUserBanks(int userId, List<String> selectedBanks) {
        try (DataBase database = DataBase.getInstance()) {
            UsersDTO user = database.getUser(userId);

            if (user != null) {
                user.setBank(selectedBanks);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserCurrency(int userId, List<String> selectedCurrency) {
        try (DataBase database = DataBase.getInstance()) {
            UsersDTO user = database.getUser(userId);

            if (user.getCurrency() != null) {
                user.setCurrency(selectedCurrency);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
