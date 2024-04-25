package com.cloud.orderservice.exception;

import lombok.Getter;

@Getter
public class OrderServiceApiException extends RuntimeException{
    private final String errorCode;

    public OrderServiceApiException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
