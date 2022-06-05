package ru.karaban.currency.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.karaban.currency.to.GiphyTO;

@FeignClient(name = "GiphyClient", url = "${giphyApi.url}")
public interface GiphyFeignClient {

    @RequestMapping(method = RequestMethod.GET)
    GiphyTO getGifUrl(@RequestParam("q") String searchValue, @RequestParam("api_key") String key);
}
