package com.cloud.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE/api/product")
public interface ProductService {

    @PutMapping("/{skuCode}")
    ResponseEntity<Void> reduceQuantity(@PathVariable String skuCode, @RequestParam Integer quantity);
}
