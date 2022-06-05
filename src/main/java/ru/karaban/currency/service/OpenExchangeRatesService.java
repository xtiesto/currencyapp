package ru.karaban.currency.service;

public interface OpenExchangeRatesService {

    Double getLatestCurrencyRate(String selectedCurrency, String basicCurrency);

    Double getYesterdayCurrencyRate(String selectedCurrency, String basicCurrency);
}
