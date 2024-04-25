package com.cloud.orderservice.service.impl;

import com.cloud.orderservice.dto.OrderRequest;
import com.cloud.orderservice.dto.OrderResponse;
import com.cloud.orderservice.dto.ProductResponse;
import com.cloud.orderservice.entity.Order;
import com.cloud.orderservice.external.client.ProductService;
import com.cloud.orderservice.repository.OrderRepository;
import com.cloud.orderservice.service.OrderService;
import com.cloud.orderservice.exception.OrderServiceApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static com.cloud.orderservice.dto.OrderResponse.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final RestTemplate restTemplate;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        log.info("Placing order {}:", orderRequest.skuCode());
        productService.reduceQuantity(orderRequest.skuCode(), orderRequest.quantity());

        Order order = Order.builder()
                .skuCode(orderRequest.skuCode())
                .price(orderRequest.price())
                .quantity(orderRequest.quantity())
                .orderDate(Instant.now())
                .build();
        orderRepository.save(order);
        return "Order Placed Successfully";
    }

    @Override
    public OrderResponse getOrderDetails(String  skuCode) {
        log.info("Getting order detail for {}", skuCode);
        Order order = orderRepository.findBySkuCodeIgnoreCase(skuCode).orElseThrow(() ->
                new OrderServiceApiException("Order not found for SkuCode " + skuCode, "404" ));

        log.info("Invoking product service to get product with id {}", order.getSkuCode());

        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/api/product/" + order.getSkuCode(),
                ProductResponse.class);

        ProductDetails productDetails = null;
        if (productResponse != null) {
            productDetails = ProductDetails.builder()
                    .skuCode(productResponse.skuCode())
                    .quantity(productResponse.quantity())
                    .price(productResponse.price())
                    .id(productResponse.id())
                    .build();
        }

        return builder()
                .id(order.getId())
                .price(order.getPrice())
                .skuCode(order.getSkuCode())
                .quantity(order.getQuantity())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .build();
    }

    @Override
    public void deleteOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()){
            throw new OrderServiceApiException("Order not found", "404");
        }
        orderRepository.deleteById(id);
    }

    @Override
    public void updateProduct(Long id, OrderRequest request) {
        Order existingProduct = orderRepository.findById(id)
                .orElseThrow(() -> new OrderServiceApiException("Product not found with id: " + id, "404"));

        if (request.skuCode() != null) {
            existingProduct.setSkuCode(request.skuCode());
        }
        if (request.quantity() != null) {
            existingProduct.setQuantity(request.quantity());
        }
        if (request.price() != null) {
            existingProduct.setPrice(request.price());
        }

        orderRepository.save(existingProduct);
    }


}
