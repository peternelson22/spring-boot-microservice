package com.cloud.orderservice.service;

import com.cloud.orderservice.dto.OrderRequest;
import com.cloud.orderservice.dto.OrderResponse;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(String  skuCode);

    void deleteOrder(Long id);

    void updateProduct(Long id, OrderRequest request);
}
