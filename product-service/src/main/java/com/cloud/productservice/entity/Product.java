package com.cloud.productservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter @ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @NotBlank(message = "Please enter the name of the product")
    private String skuCode;
    @DecimalMin(value = "0.1", inclusive = true)
    private BigDecimal price;
    @NotNull(message = "Quantity is required")
    private Integer quantity;

}
