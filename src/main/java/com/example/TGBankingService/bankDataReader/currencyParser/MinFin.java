package com.example.TGBankingService.bankDataReader.currencyParser;

import com.example.TGBankingService.bankDataReader.dto.BankData;
import com.example.TGBankingService.bankDataReader.enums.BanksName;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinFin {

    public static List<BankData> сurrencyParser(String currency) throws IOException {
        String url = "https://minfin.com.ua/ua/currency/banks/" + currency;

        List<BankData> bankDataList = new ArrayList<>();

        Connection connection = Jsoup.connect(url);
        connection.userAgent("Mozilla/5.0");
        Document document = connection.ignoreContentType(true).get();

        Elements postName = document.getElementsByAttributeValue("class", "js-ex-rates mfcur-table-bankname");
        String htmlString = postName.toString();
        String patternString = "data-title=\"([^\"]*)\" data-card=\"([^\"]*)\"><a .*?>(.*?)</a>";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(htmlString);

        while (matcher.find()) {
            String dataTitle = matcher.group(1);
            String name = matcher.group(3).replaceAll("<span[^>]*>.*?</span>", "").toUpperCase().trim();

            double priceToBuy = extractPrice(dataTitle.replaceAll("^(.*?)(?=/)", ""));
            double priceForSale = extractPrice(dataTitle.replaceAll("/(.*)", ""));

            BankData bankData = new BankData(currency, name, priceToBuy, priceForSale);

            try {
                if (bankData.getName().equals(BanksName.MONOBANK.toString())) {
                    for (BankData data : bankDataList) {
                        if (data.getName().equals(BanksName.UNIVERSALBANK.toString())) {
                            bankData.setPriceToBuy(data.getPriceToBuy());
                            bankData.setPriceForSale(data.getPriceForSale());
                            bankDataList.add(bankData);
                        }
                    }
                } else {
                    bankDataList.add(bankData);
                }
            } catch (RuntimeException e) {

            }

        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("src/main/resources/BankData.json"), bankDataList);

        return bankDataList;
    }

    private static double extractPrice(String data) {
        String pricePattern = "\\d+\\.\\d+";

        Pattern pattern = Pattern.compile(pricePattern);
        Matcher matcher = pattern.matcher(data);

        double price = 0.0;

        if (matcher.find()) {
            String priceStr = matcher.group();
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return price;
    }
}
