package com.cloud.orderservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "order_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Quantity can not be null, please enter a valid quantity")
    private Integer quantity;

    @NotBlank(message = "Please enter the name of the product")
    private String skuCode;
    @DecimalMin(value = "0.1", inclusive = true)
    private BigDecimal price;
    private Instant orderDate;

}
