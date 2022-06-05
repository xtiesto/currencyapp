package ru.karaban.currency.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CurrencyServiceImplTest {

    private final static String POSITIVE_GIF = "positive gif url";
    private final static String NEGATIVE_GIF = "negative gif url";
    private final static String CURRENCY = "RUB";

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @Mock
    private OpenExchangeRatesService openExchangeRatesService;

    @Mock
    private GiphyService giphyService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(giphyService.getPositiveGifUrl()).thenReturn(POSITIVE_GIF);
        when(giphyService.getNegativeGifUrl()).thenReturn(NEGATIVE_GIF);
    }

    @Test
    void testCurrencyRateChangeWhenLatestCurrencyIsMoreThanYesterdayCurrency() {
        when(openExchangeRatesService.getLatestCurrencyRate(any(), any())).thenReturn(2.0);
        when(openExchangeRatesService.getYesterdayCurrencyRate(any(), any())).thenReturn(1.0);

        String gifUrl = currencyService.getCurrencyRateChange(CURRENCY);

        assertEquals(POSITIVE_GIF, gifUrl);
    }

    @Test
    void getCurrencyRateChangeWhenLatestCurrencyIsLessThanYesterdayCurrency() {
        when(openExchangeRatesService.getLatestCurrencyRate(any(), any())).thenReturn(1.0);
        when(openExchangeRatesService.getYesterdayCurrencyRate(any(), any())).thenReturn(2.0);

        String gifUrl = currencyService.getCurrencyRateChange(CURRENCY);

        assertEquals(NEGATIVE_GIF, gifUrl);
    }

    @Test
    void getCurrencyRateChangeWhenLatestCurrencyEqualsYesterdayCurrency() {
        when(openExchangeRatesService.getLatestCurrencyRate(any(), any())).thenReturn(2.0);
        when(openExchangeRatesService.getYesterdayCurrencyRate(any(), any())).thenReturn(2.0);

        String gifUrl = currencyService.getCurrencyRateChange(CURRENCY);

        assertEquals(POSITIVE_GIF, gifUrl);
    }
}