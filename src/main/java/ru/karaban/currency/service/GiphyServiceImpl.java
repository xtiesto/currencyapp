package ru.karaban.currency.service;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.karaban.currency.feignclient.GiphyFeignClient;
import ru.karaban.currency.to.GiphyTO;

import static feign.Util.checkNotNull;

@Service
public class GiphyServiceImpl implements GiphyService {

    Logger logger = LoggerFactory.getLogger(GiphyServiceImpl.class);

    @Autowired
    private GiphyFeignClient giphyFeignClient;

    @Value("${giphyApi.key}")
    private String giphyApiKey;

    @Override
    public String getPositiveGifUrl() {
        logger.info("Getting positive gif url");
        String positiveGifUrl = getGifUrl("rich");
        logger.info("Getting positive gif url is completed " + positiveGifUrl);
        return positiveGifUrl;
    }

    @Override
    public String getNegativeGifUrl() {
        logger.info("Getting negative gif url");
        String negativeGifUrl = getGifUrl("broke");
        logger.info("Getting negative gif url is completed " + negativeGifUrl);
        return negativeGifUrl;
    }

    private String getGifUrl(String searchValue) {
        GiphyTO responseGiphy;
        try {
            responseGiphy = giphyFeignClient.getGifUrl(searchValue, giphyApiKey);
        } catch (FeignException feignException) {
            throw new RuntimeException("Feign client error", feignException);
        }
        checkNotNull(responseGiphy, "Response object should not be null");
        return getRandomGifUrl(responseGiphy);
    }

    private String getRandomGifUrl(GiphyTO responseGiphy) {
        try {
            int randomGifIndex = (int) (Math.random() * responseGiphy.getData().size());
            return responseGiphy.getData().get(randomGifIndex).getImages().getOriginal().getUrl();
        } catch (RuntimeException runtimeException) {
            throw new RuntimeException("Could not decode Giphy response", runtimeException);
        }
    }
}
