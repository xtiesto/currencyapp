package ru.karaban.currency.service;

// todo Почитай почему для бинов Service, Component и Repository нужны интерфейса,
//  а для Controller и RestController нет. Заодно, чем эти аннотации отличаются.
public interface CurrencyService {

    String getCurrencyRateChange(String selectedCurrency);
}
