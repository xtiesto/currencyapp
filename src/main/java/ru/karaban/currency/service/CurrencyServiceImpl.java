package ru.karaban.currency.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// todo Почитать про @Scope https://www.baeldung.com/spring-bean-scopes
@Service
public class CurrencyServiceImpl implements CurrencyService {

    Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Autowired
    private OpenExchangeRatesService openExchangeRatesService;

    @Autowired
    private GiphyService giphyService;

    @Value("${basicCurrency}")
    private String basicCurrency;

    @Override
    public String getCurrencyRateChange(String selectedCurrency) {
        logger.info("Getting currency rates");
        Double todayCurrencyRate = openExchangeRatesService.getLatestCurrencyRate(selectedCurrency, basicCurrency);
        Double yesterdayCurrencyRate = openExchangeRatesService.getYesterdayCurrencyRate(selectedCurrency, basicCurrency);
        logger.info("Comparing currency rates");
        if (todayCurrencyRate >= yesterdayCurrencyRate) {
            logger.info("Latest currency rate is more than yesterday's. Returning positive gif");
            return giphyService.getPositiveGifUrl();
        } else {
            logger.info("Latest currency rate is less than yesterday's. Returning negative gif");
            return giphyService.getNegativeGifUrl();
        }
    }
}
