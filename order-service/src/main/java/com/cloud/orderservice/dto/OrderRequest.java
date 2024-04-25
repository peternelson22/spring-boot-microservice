package com.cloud.orderservice.dto;

import java.math.BigDecimal;

public record OrderRequest(Integer quantity, String skuCode, BigDecimal price ) {
}
