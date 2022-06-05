package ru.karaban.currency.to;

import java.util.Map;

public class ExchangeRatesTO {

    private Map<String, Double> rates;

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
