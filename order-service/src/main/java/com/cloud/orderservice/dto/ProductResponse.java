package com.cloud.orderservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(Long id, String skuCode, BigDecimal price, Integer quantity) {
}