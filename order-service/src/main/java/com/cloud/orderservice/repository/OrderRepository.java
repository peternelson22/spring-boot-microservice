package com.cloud.orderservice.repository;

import com.cloud.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findBySkuCodeIgnoreCase(String skuCode);

}
