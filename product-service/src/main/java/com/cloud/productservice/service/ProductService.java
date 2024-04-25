package com.cloud.productservice.service;

import com.cloud.productservice.dto.ProductRequest;
import com.cloud.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();

    void createProduct(ProductRequest productRequest);

    void reduceQuantity(String  skuCode, Integer quantity);

    void deleteProduct(long id);

    ProductResponse getProductBySkuCode(String skuCode);
}
