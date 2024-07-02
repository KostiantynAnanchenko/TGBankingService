package com.example.TGBankingService.bankDataReader.dto;

public class BankData {
    private String currencyCode;
    private String name;
    private double priceToBuy;
    private double priceForSale;

    public BankData(String currencyCode, String name, double priceToBuy, double priceForSale) {
        this.currencyCode = currencyCode;
        this.name = name;
        this.priceToBuy = priceToBuy;
        this.priceForSale = priceForSale;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceToBuy() {
        return priceToBuy;
    }

    public void setPriceToBuy(double priceToBuy) {
        this.priceToBuy = priceToBuy;
    }

    public double getPriceForSale() {
        return priceForSale;
    }

    public void setPriceForSale(double priceForSale) {
        this.priceForSale = priceForSale;
    }


}
