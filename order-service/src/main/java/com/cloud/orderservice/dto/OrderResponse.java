package com.cloud.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long id;
    private String skuCode;
    private Integer quantity;
    private BigDecimal price;
    private Instant orderDate;
    private ProductDetails productDetails;

    @AllArgsConstructor @Data
    @NoArgsConstructor @Builder
    public static class ProductDetails {

        private long id;
        private String skuCode;
        private BigDecimal price;
        private Integer quantity;

    }
}

