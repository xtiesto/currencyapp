package ru.karaban.currency.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karaban.currency.to.ExchangeRatesTO;


@FeignClient(name = "OpenExchangeRatesClient", url = "${openExchangeRatesApi.url}")
public interface OpenExchangeRatesFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/latest.json")
    ExchangeRatesTO getLatestCurrencyRate(
            @RequestParam("base") String basicCurrency,
            @RequestParam("app_id") String appId);

    @RequestMapping(method = RequestMethod.GET, value = "/historical/{date}.json")
    ExchangeRatesTO getHistoricalCurrencyRare(
            @PathVariable("date") String date,
            @RequestParam("base") String basicCurrency,
            @RequestParam("app_id") String appId);
}
