package com.cloud.orderservice.external.decoder;

import com.cloud.orderservice.exception.OrderServiceApiException;
import com.cloud.orderservice.external.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(),
                    ErrorResponse.class);
            return new OrderServiceApiException(errorResponse.getErrorMessage(), errorResponse.getErrorCode());
        } catch (IOException e) {
            throw new OrderServiceApiException("Product may not have the given quantity", "INSUFFICIENT");
        }
    }
}
