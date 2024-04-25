package com.cloud.productservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {

    private String skuCode;
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
