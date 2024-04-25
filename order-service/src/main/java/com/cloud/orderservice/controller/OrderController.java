package com.cloud.orderservice.controller;

import com.cloud.orderservice.dto.OrderRequest;
import com.cloud.orderservice.dto.OrderResponse;
import com.cloud.orderservice.entity.Order;
import com.cloud.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String > placeOrder(@Valid @RequestBody OrderRequest orderRequest){
        return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable String skuCode){
        OrderResponse orderResponse = orderService.getOrderDetails(skuCode);
        return  new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        orderService.updateProduct(id, request);
        return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
