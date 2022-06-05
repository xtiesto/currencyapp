package ru.karaban.currency.service;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.karaban.currency.feignclient.OpenExchangeRatesFeignClient;
import ru.karaban.currency.to.ExchangeRatesTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static feign.Util.checkNotNull;

@Service
public class OpenExchangeRatesServiceImpl implements OpenExchangeRatesService {

    Logger logger = LoggerFactory.getLogger(OpenExchangeRatesServiceImpl.class);

    @Autowired
    private OpenExchangeRatesFeignClient openExchangeRatesFeignClient;

    @Value("${openExchangeRatesApi.key}")
    private String openExchangeRatesApiKey;

    @Override
    public Double getLatestCurrencyRate(String selectedCurrency, String basicCurrency) {
        ExchangeRatesTO exchangeRates;
        logger.info("Getting latest currency rate from OpenExchangeRates service");
        try {
            exchangeRates = openExchangeRatesFeignClient.getLatestCurrencyRate(basicCurrency, openExchangeRatesApiKey);
        } catch (FeignException feignException) {
            throw new RuntimeException("Feign client error", feignException);
        }
        checkNotNull(exchangeRates, "Response object should not be null");
        Double exchangeRate = getExchangeRate(selectedCurrency, exchangeRates);
        logger.info(
                String.format("Latest %s rate to %s from OpenExchangeRates service is " + exchangeRate,
                        selectedCurrency, basicCurrency));
        return exchangeRate;
    }

    @Override
    public Double getYesterdayCurrencyRate(String selectedCurrency, String basicCurrency) {
        ExchangeRatesTO exchangeRates;
        logger.info("Getting yesterday's currency rate from OpenExchangeRates service");
        try {
            exchangeRates = openExchangeRatesFeignClient.getHistoricalCurrencyRare(
                    getYesterday(), basicCurrency,openExchangeRatesApiKey);
        } catch (FeignException feignException) {
            throw new RuntimeException("Feign client error", feignException);
        }
        checkNotNull(exchangeRates, "Response object should not be null");
        Double exchangeRate = getExchangeRate(selectedCurrency, exchangeRates);
        logger.info(
                String.format("Yesterday's %s rate to %s from OpenExchangeRates service is " + exchangeRate,
                        selectedCurrency, basicCurrency));
        return exchangeRate;
    }

    private String getYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private Double getExchangeRate(String selectedCurrency, ExchangeRatesTO exchangeRates) {
        checkNotNull(exchangeRates.getRates(), "Exchange rates should not be null");
        return exchangeRates.getRates().entrySet().stream()
                .filter(entry -> selectedCurrency.equals(entry.getKey()))
                .findAny().orElseThrow(() -> new IllegalArgumentException(
                        String.format("Could not find currency %s in OpenExchangeRates response", selectedCurrency))).getValue();
    }
}
