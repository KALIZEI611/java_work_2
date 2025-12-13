package HW_Module_08;


public class CurrencyFactory {

    public static Currency createCurrency(String code) {
        switch (code.toUpperCase()) {
            case "USD":
                return Currency.USD;
            case "EUR":
                return Currency.EUR;
            case "GBP":
                return Currency.GBP;
            case "JPY":
                return Currency.JPY;
            default:
                throw new IllegalArgumentException("Неизвестная валюта: " + code);
        }
    }

    public static Currency[] getAllCurrencies() {
        return Currency.values();
    }

    public static String getCurrencyInfo(Currency currency) {
        return currency.getName() + " (курс к USD: " + currency.getRateToUSD() + ")";
    }
}