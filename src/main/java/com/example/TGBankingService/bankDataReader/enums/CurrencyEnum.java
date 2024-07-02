package com.example.TGBankingService.bankDataReader.enums;

public enum CurrencyEnum {
    USD,
    EUR,
    GBP,
    CHF,
    SEK,
    PLN,
    NOK,
    JPY,
    DKK,
    CNY,
    CAD,
    AUD,
    HUF,
    CZK,
    ISL;

    @Override
    public String toString() {
        switch (this) {
            case USD:
                return "USD";
            case EUR:
                return "EUR";
            case GBP:
                return "GBP";
            case CHF:
                return "CHF";
            case SEK:
                return "SEK";
            case PLN:
                return "PLN";
            case NOK:
                return "NOK";
            case JPY:
                return "JPY";
            case DKK:
                return "DKK";
            case CNY:
                return "CNY";
            case CAD:
                return "CAD";
            case AUD:
                return "AUD";
            case HUF:
                return "HUF";
            case CZK:
                return "CZK";
            case ISL:
                return "ISL";
            default:
                return "Невідома валюта";
        }
    }
}
