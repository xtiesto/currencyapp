package ru.karaban.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.karaban.currency.service.CurrencyService;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping(value = "/currency/{value}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getCurrencyExchangeRateChange(@PathVariable("value") String selectedCurrency) {
        return "<html><img src=\"" + currencyService.getCurrencyRateChange(selectedCurrency) +"\"></html>";
    }
}
