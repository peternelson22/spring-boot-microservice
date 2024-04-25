package com.cloud.productservice.controller;

import com.cloud.productservice.dto.ProductRequest;
import com.cloud.productservice.dto.ProductResponse;
import com.cloud.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return new ResponseEntity<>("Successfully created", CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<ProductResponse> getProductBySkuCode(@PathVariable String  skuCode){
        return ResponseEntity.ok(productService.getProductBySkuCode(skuCode));
    }

    @PutMapping("/{skuCode}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable String skuCode, @RequestParam Integer quantity){
        productService.reduceQuantity(skuCode, quantity);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
