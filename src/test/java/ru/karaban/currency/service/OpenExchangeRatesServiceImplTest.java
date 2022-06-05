package ru.karaban.currency.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.karaban.currency.feignclient.OpenExchangeRatesFeignClient;
import ru.karaban.currency.to.ExchangeRatesTO;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.karaban.currency.service.ServiceTestHelper.getFeignClientException;

class OpenExchangeRatesServiceImplTest {

    private final static String SELECTED_CURRENCY = "RUB";
    private final static String BASIC_CURRENCY = "USD";


    @InjectMocks
    private OpenExchangeRatesServiceImpl openExchangeRatesService;

    @Mock
    private OpenExchangeRatesFeignClient openExchangeRatesFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLatestCurrencyRate() {
        when(openExchangeRatesFeignClient.getLatestCurrencyRate(any(), any())).thenReturn(exchangeRatesTO());
        openExchangeRatesService.getLatestCurrencyRate(SELECTED_CURRENCY, BASIC_CURRENCY);
    }

    @Test
    void testGetLatestCurrencyRateWhenReturnIsNull() {
        when(openExchangeRatesFeignClient.getLatestCurrencyRate(any(), any())).thenReturn(null);

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class,
                () -> openExchangeRatesService.getLatestCurrencyRate(SELECTED_CURRENCY, BASIC_CURRENCY));
        assertTrue(thrown.getMessage().contains("Response object should not be null"));
    }

    @Test
    void testGetLatestCurrencyRateWhenFeignExceptionIsThrown() {
        when(openExchangeRatesFeignClient.getLatestCurrencyRate(any(), any())).thenThrow(getFeignClientException());
        
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> openExchangeRatesService.getLatestCurrencyRate(SELECTED_CURRENCY, BASIC_CURRENCY));
        assertTrue(thrown.getMessage().contains("Feign client error"));
    }

    @Test
    void testGetLatestCurrencyRateWhenCurrencyNotFound() {
        when(openExchangeRatesFeignClient.getLatestCurrencyRate(any(), any())).thenReturn(exchangeRatesTO());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> openExchangeRatesService.getLatestCurrencyRate("Some strange currency", BASIC_CURRENCY));
        assertTrue(thrown.getMessage().contains("Could not find currency"));
    }

    @Test
    void getYesterdayCurrencyRare() {
        when(openExchangeRatesFeignClient.getHistoricalCurrencyRare(any(), any(), any())).thenReturn(exchangeRatesTO());
        openExchangeRatesService.getYesterdayCurrencyRate(SELECTED_CURRENCY, BASIC_CURRENCY);
    }

    @Test
    void testGetYesterdayCurrencyRateWhenReturnIsNull() {
        when(openExchangeRatesFeignClient.getHistoricalCurrencyRare(any(), any(), any())).thenReturn(null);

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class,
                () -> openExchangeRatesService.getYesterdayCurrencyRate(SELECTED_CURRENCY, BASIC_CURRENCY));
        assertTrue(thrown.getMessage().contains("Response object should not be null"));
    }

    @Test
    void testGetYesterdayCurrencyRateWhenFeignExceptionIsThrown() {
        when(openExchangeRatesFeignClient.getHistoricalCurrencyRare(any(), any(), any())).thenThrow(getFeignClientException());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> openExchangeRatesService.getYesterdayCurrencyRate(SELECTED_CURRENCY, BASIC_CURRENCY));
        assertTrue(thrown.getMessage().contains("Feign client error"));
    }

    @Test
    void testGetYesterdayCurrencyRateWhenCurrencyNotFound() {
        when(openExchangeRatesFeignClient.getHistoricalCurrencyRare(any(), any(), any())).thenReturn(exchangeRatesTO());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> openExchangeRatesService.getYesterdayCurrencyRate("Some strange currency", BASIC_CURRENCY));
        assertTrue(thrown.getMessage().contains("Could not find currency"));
    }

    private ExchangeRatesTO exchangeRatesTO() {
        ExchangeRatesTO exchangeRatesTO = new ExchangeRatesTO ();
        Map<String, Double> rates = new HashMap<>();
        rates.put(SELECTED_CURRENCY, 2.0);
        rates.put("EUR", 3.0);
        exchangeRatesTO.setRates(rates);
        return exchangeRatesTO;
    }
}