package ru.karaban.currency.service;

import feign.FeignException;
import feign.Request;

import java.util.HashMap;

public class ServiceTestHelper {

    static FeignException.FeignClientException getFeignClientException() {
        return new FeignException.FeignClientException(1, "", Request.create("GET", "null", new HashMap<>(), null, null), null, null);
    }
}
