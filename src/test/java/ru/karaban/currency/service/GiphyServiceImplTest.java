package ru.karaban.currency.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.karaban.currency.feignclient.GiphyFeignClient;
import ru.karaban.currency.to.GiphyTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.karaban.currency.service.ServiceTestHelper.getFeignClientException;

class GiphyServiceImplTest {

    private final static String URL = "url";

    @InjectMocks
    private GiphyServiceImpl giphyService;

    @Mock
    private GiphyFeignClient giphyFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPositiveGifUrl() {
        when(giphyFeignClient.getGifUrl(any(), any())).thenReturn(getCorrectGiphyTO());
        assertNotNull(giphyService.getPositiveGifUrl());
    }

    @Test
    void getPositiveGifUrlWhenResponseIsNull() {
        when(giphyFeignClient.getGifUrl(any(), any())).thenReturn(null);

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> giphyService.getPositiveGifUrl());
        assertTrue(thrown.getMessage().contains("Response object should not be null"));
    }

    @Test
    void getPositiveGifUrlWhenFeignExceptionIsThrown() {
        when(giphyFeignClient.getGifUrl(any(), any())).thenThrow(getFeignClientException());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> giphyService.getPositiveGifUrl());
        assertTrue(thrown.getMessage().contains("Feign client error"));
    }

    @Test
    void getPositiveGifUrlWhenCouldNotDecodeResponseObject() {
        when(giphyFeignClient.getGifUrl(any(), any())).thenReturn(new GiphyTO());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> giphyService.getPositiveGifUrl());
        assertTrue(thrown.getMessage().contains("Could not decode Giphy response"));
    }

    @Test
    void getNegativeGifUrl() {
        when(giphyFeignClient.getGifUrl(any(), any())).thenReturn(getCorrectGiphyTO());
        assertNotNull(giphyService.getPositiveGifUrl());
    }

    @Test
    void getNegativeGifUrlWhenResponseIsNull() {
        when(giphyFeignClient.getGifUrl(any(), any())).thenReturn(null);

        NullPointerException thrown = Assertions.assertThrows(NullPointerException.class, () -> giphyService.getNegativeGifUrl());
        assertTrue(thrown.getMessage().contains("Response object should not be null"));
    }

    @Test
    void getNegativeGifUrlWhenFeignExceptionIsThrown() {
        when(giphyFeignClient.getGifUrl(any(), any())).thenThrow(getFeignClientException());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> giphyService.getNegativeGifUrl());
        assertTrue(thrown.getMessage().contains("Feign client error"));
    }

    @Test
    void getNegativeGifUrlWhenCouldNotDecodeResponseObject() {
        when(giphyFeignClient.getGifUrl(any(), any())).thenReturn(new GiphyTO());

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> giphyService.getNegativeGifUrl());
        assertTrue(thrown.getMessage().contains("Could not decode Giphy response"));
    }

    private GiphyTO getCorrectGiphyTO() {
        GiphyTO giphyTO = new GiphyTO();
        giphyTO.setData(List.of(createResult(), createResult()));
        return giphyTO;
    }

    private GiphyTO.Result createResult() {
        GiphyTO.Original original = new GiphyTO.Original();
        original.setUrl(URL);
        GiphyTO.Images images = new GiphyTO.Images();
        images.setOriginal(original);
        GiphyTO.Result result = new GiphyTO.Result();
        result.setImages(images);
        return result;
    }
}